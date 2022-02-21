package com.hbsf.arouter_compiler;

import com.google.auto.service.AutoService;
import com.hbsf.arouter_annotation.ARouter;
import com.hbsf.arouter_annotation.bean.RouterBean;
import com.hbsf.arouter_compiler.util.ProcessorConfig;
import com.hbsf.arouter_compiler.util.ProcessorUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * 同学们注意：编码此类，记住就是一个字（细心，细心，细心），出了问题debug真的不好调试
 */

// AutoService则是固定的写法，加个注解即可
// 通过auto-service中的@AutoService可以自动生成AutoService注解处理器，用来注册
// 用来生成 META-INF/services/javax.annotation.processing.Processor 文件
@AutoService(Processor.class)

// 允许/支持的注解类型，让注解处理器处理
@SupportedAnnotationTypes({ProcessorConfig.AROUTER_PACKAGE})

// 指定JDK编译版本
@SupportedSourceVersion(SourceVersion.RELEASE_7)

// 注解处理器接收的参数
@SupportedOptions({ProcessorConfig.OPTIONS, ProcessorConfig.APT_PACKAGE})

public class ARouterProcessor extends AbstractProcessor {

    // 操作Element的工具类（类，函数，属性，其实都是Element）
    private Elements elementTool;

    // type(类信息)的工具类，包含用于操作TypeMirror的工具方法
    private Types typeTool;

    // Message用来打印 日志相关信息
    private Messager messager;

    // 文件生成器， 类 资源 等，就是最终要生成的文件 是需要Filer来完成的
    private Filer filer;

    private String options; // 各个模块传递过来的模块名 例如：app order personal
    private String aptPackage; // 各个模块传递过来的目录 用于统一存放 apt生成的文件

    // 生成path文件的中间体，一个组可能有好多路径，所以是list
    private Map<String, List<RouterBean>> mAllPathMap = new HashMap<>(); // 目前是一个

    // 生成group文件的中间体，其实后期生成文件只需要String，类名就可以了
    private Map<String, String> mAllGroupMap = new HashMap<>();

    private int i = 0;

    // 做初始化工作，就相当于 Activity中的 onCreate函数一样的作用
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        elementTool = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
        typeTool = processingEnvironment.getTypeUtils();

        // 只有接受到 App壳 传递过来的书籍，才能证明我们的 APT环境搭建完成
        options = processingEnvironment.getOptions().get(ProcessorConfig.OPTIONS);
        aptPackage = processingEnvironment.getOptions().get(ProcessorConfig.APT_PACKAGE);
        messager.printMessage(Diagnostic.Kind.NOTE, ">>>>>>>>>>>>>>>>>>>>>> options:" + options);
        messager.printMessage(Diagnostic.Kind.NOTE, ">>>>>>>>>>>>>>>>>>>>>> aptPackage:" + aptPackage);
        if (options != null && aptPackage != null) {
            messager.printMessage(Diagnostic.Kind.NOTE, "APT Successful");
        } else {
            messager.printMessage(Diagnostic.Kind.NOTE, "APT problem");
        }
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set.isEmpty()) {
            messager.printMessage(Diagnostic.Kind.NOTE, "No place to user ARouter");
            return false;
        }

        //图片传输相关
        TypeElement callType = elementTool.getTypeElement(ProcessorConfig.CALL);
        TypeMirror callMirror = callType.asType();

        // 全部被注解的集合，通过这个可以拿到相应的Activity
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ARouter.class);

        // 获取Activity，后期进行注解注解地方的判断是否符合要求
        TypeElement activityType = elementTool.getTypeElement(ProcessorConfig.ACTIVITY_PACKAGE);
        // 拿到Activity类信息
        TypeMirror activityMirror = activityType.asType();

        // 遍历所有的被ARouter注解的节点
        for (Element element : elements) {

            // 获取类名，比如MainActivity
            String className = element.getSimpleName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE, "@ARetuer  : " + className);

            // 拿到注解
            ARouter aRouter = element.getAnnotation(ARouter.class);

            // 创建路由对象
            RouterBean routerBean = new RouterBean.Builder()
                    .addGroup(aRouter.group())
                    .addPath(aRouter.path())
                    .addElement(element)
                    .build();
            // ARouter注解的类 必须继承 Activity
            TypeMirror elementMirror = element.asType(); // Main2Activity的具体详情 例如：继承了 Activity
            if (typeTool.isSubtype(elementMirror, activityMirror)) {
                routerBean.setTypeEnum(RouterBean.TypeEnum.ACTIVITY);
            } else if (typeTool.isSubtype(elementMirror, callMirror)) {
                routerBean.setTypeEnum(RouterBean.TypeEnum.CALL);
            } else {
                throw new RuntimeException("@ARouter must be in ctivity above");
            }

            if (checkRouterPath(routerBean)) {
                messager.printMessage(Diagnostic.Kind.NOTE, "RouterBean Check Success:" + routerBean.toString());

                // 赋值 mAllPathMap 集合里面去
                List<RouterBean> routerBeans = mAllPathMap.get(routerBean.getGroup());

                // 如果从Map中找不到key为：bean.getGroup()的数据，就新建List集合再添加进Map
                if (ProcessorUtils.isEmpty(routerBeans)) {
                    routerBeans = new ArrayList<>();
                    routerBeans.add(routerBean);
                    mAllPathMap.put(routerBean.getGroup(), routerBeans);
                } else {
                    routerBeans.add(routerBean);
                }

            } else {
                messager.printMessage(Diagnostic.Kind.ERROR, "path error");
            }
        }

        //两个接口，我们生成的类要继承这两个接口
        TypeElement pathType = elementTool.getTypeElement(ProcessorConfig.AROUTER_API_PATH); // ARouterPath描述
        TypeElement groupType = elementTool.getTypeElement(ProcessorConfig.AROUTER_API_GROUP); // ARouterGroup描述

        // TODO 第一大步：生成Path类
        try {
            createPathFile(pathType);
        } catch (IOException e) {
            e.printStackTrace();
            messager.printMessage(Diagnostic.Kind.NOTE, "creat path error e:" + e.getMessage());
        }

        // TODO 第二大步：生成Group类
        try {
            createGroupFile(groupType, pathType);
        } catch (IOException e) {
            e.printStackTrace();
            messager.printMessage(Diagnostic.Kind.NOTE, "creat group error e:" + e.getMessage());
        }

        return true;
    }

    /**
     * 生成路由组Group文件，如：ARouter$$Group$$app
     * @param groupType ARouterLoadGroup接口信息
     * @param pathType ARouterLoadPath接口信息
     */
    private void createGroupFile(TypeElement groupType, TypeElement pathType) throws IOException {
        if (ProcessorUtils.isEmpty(mAllGroupMap) || ProcessorUtils.isEmpty(mAllPathMap)) return;

        // 返回值 这一段 Map<String, Class<? extends ARouterPath>>
        TypeName methodReturns = ParameterizedTypeName.get(
                ClassName.get(Map.class),        // Map
                ClassName.get(String.class),    // Map<String,

                // Class<? extends ARouterPath>> 难度
                ParameterizedTypeName.get(ClassName.get(Class.class),
                        // ? extends ARouterPath
                        WildcardTypeName.subtypeOf(ClassName.get(pathType))) // ? extends ARouterLoadPath
                        // WildcardTypeName.supertypeOf() 做实验 ? super

                // 最终的：Map<String, Class<? extends ARouterPath>>
        );

        // 1.方法 public Map<String, Class<? extends ARouterPath>> getGroupMap() {
        MethodSpec.Builder methodBuidler = MethodSpec.methodBuilder(ProcessorConfig.GROUP_METHOD_NAME) // 方法名
                .addAnnotation(Override.class) // 重写注解 @Override
                .addModifiers(Modifier.PUBLIC) // public修饰符
                .returns(methodReturns); // 方法返回值

        // Map<String, Class<? extends ARouterPath>> groupMap = new HashMap<>();
        methodBuidler.addStatement("$T<$T, $T> $N = new $T<>()",
                ClassName.get(Map.class),
                ClassName.get(String.class),

                // Class<? extends ARouterPath> 难度
                ParameterizedTypeName.get(ClassName.get(Class.class),
                        WildcardTypeName.subtypeOf(ClassName.get(pathType))), // ? extends ARouterPath
                        ProcessorConfig.GROUP_VAR1,
                        ClassName.get(HashMap.class));

        for (Map.Entry<String, String> entry : mAllGroupMap.entrySet()) {
            methodBuidler.addStatement("$N.put($S, $T.class)",
                    ProcessorConfig.GROUP_VAR1, // groupMap.put
                    entry.getKey(), // login,app
                    ClassName.get(aptPackage + ProcessorConfig.PACKAGE_PATH_NAME, entry.getValue()));
        }

        // return groupMap;
        methodBuidler.addStatement("return $N", ProcessorConfig.GROUP_VAR1);

        // 最终生成的类文件名 ARouter$$Group$$ + personal
        String finalClassName = ProcessorConfig.GROUP_FILE_NAME + options;

        messager.printMessage(Diagnostic.Kind.NOTE, "APT creat group path :" +
                aptPackage + "." + finalClassName);

        // 生成类文件：ARouter$$Group$$app
        JavaFile.builder(aptPackage + ProcessorConfig.PACKAGE_GEGROUP_NAME, // 包名
                TypeSpec.classBuilder(finalClassName) // 类名
                .addSuperinterface(ClassName.get(groupType)) // 实现ARouterLoadGroup接口 implements ARouterGroup
                .addModifiers(Modifier.PUBLIC) // public修饰符
                .addMethod(methodBuidler.build()) // 方法的构建（方法参数 + 方法体）
                .build()) // 类构建完成
                .build() // JavaFile构建完成
                .writeTo(filer); // 文件生成器开始生成类文件
    }

    /**
     * 系列Path的类  生成工作
     * @param pathType ARouterPath 高层的标准
     * @throws IOException
     */
    private void createPathFile(TypeElement pathType) throws IOException {
        // 判断 map仓库中，是否有需要生成的文件
        if (ProcessorUtils.isEmpty(mAllPathMap)) {
            return;
        }

        //返回值
        TypeName methodReturn = ParameterizedTypeName.get(
                  ClassName.get(Map.class),         // Map
                  ClassName.get(String.class),      // Map<String,
                  ClassName.get(RouterBean.class)   // Map<String, RouterBean>
        );

        // 便利中间map，依次创建类
        for (Map.Entry<String, List<RouterBean>> entry : mAllPathMap.entrySet()) {
            // 方法
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(ProcessorConfig.PATH_METHOD_NAME)
                    .addAnnotation(Override.class) // 给方法上添加注解  @Override
                    .addModifiers(Modifier.PUBLIC) // public修饰符
                    .returns(methodReturn) // 把Map<String, RouterBean> 加入方法返回
                    ;

            // Map<String, RouterBean> pathMap = new HashMap<>(); // $N == 变量 为什么是这个，因为变量有引用 所以是$N
            methodBuilder.addStatement("$T<$T, $T> $N = new $T<>()",
                    ClassName.get(Map.class),           // Map
                    ClassName.get(String.class),        // Map<String,
                    ClassName.get(RouterBean.class),    // Map<String, RouterBean>
                    ProcessorConfig.PATH_VAR1,          // Map<String, RouterBean> pathMap
                    ClassName.get(HashMap.class)        // Map<String, RouterBean> pathMap = new HashMap<>();
                    );

            // 同组下会有好几个类
            List<RouterBean> pathList = entry.getValue();
            /**
                $N == 变量 变量有引用 所以 N
                $L == TypeEnum.ACTIVITY
             */
            for (RouterBean bean : pathList) {
                methodBuilder.addStatement("$N.put($S, $T.create($T.$L, $T.class, $S, $S))",
                        ProcessorConfig.PATH_VAR1, // pathMap.put
                        bean.getPath(), // "/login/loginActivity"
                        ClassName.get(RouterBean.class), // RouterBean
                        ClassName.get(RouterBean.TypeEnum.class), // RouterBean.Type
                        bean.getTypeEnum(), // 枚举类型：ACTIVITY
                        ClassName.get((TypeElement) bean.getElement()), // LoginActivity.class
                        bean.getPath(), // 路径名
                        bean.getGroup() // 组名
                        );
            } // TODO end for

            // return pathMap;
            methodBuilder.addStatement("return $N", ProcessorConfig.PATH_VAR1);

            // TODO 注意：不能像以前一样，1.方法，2.类  3.包， 因为这里面有implements ，所以 方法和类要合为一体生成才行，这是特殊情况

            // 最终生成的类文件名  ARouter$$Path$$login
            String finalClassName = ProcessorConfig.PATH_FILE_NAME + entry.getKey();

            messager.printMessage(Diagnostic.Kind.NOTE, "APT creat path :" +
                    aptPackage + "." + finalClassName);

            // 生成类文件：ARouter$$Path$$Login
            JavaFile.builder(aptPackage + ProcessorConfig.PACKAGE_PATH_NAME, // 包名  APT 存放的路径
                    TypeSpec.classBuilder(finalClassName) // 类名
                            .addSuperinterface(ClassName.get(pathType)) // 实现ARouterLoadPath接口  implements ARouterPath==pathType
                            .addModifiers(Modifier.PUBLIC) // public修饰符
                            .addMethod(methodBuilder.build()) // 方法的构建（方法参数 + 方法体）
                            .build()) // 类构建完成
                    .build() // JavaFile构建完成
                    .writeTo(filer); // 文件生成器开始生成类文件

            //  给group生成类的中间map赋值
            mAllGroupMap.put(entry.getKey(), finalClassName);
        }
    }

    /**
     * 校验@ARouter注解的值，如果group未填写就从必填项path中截取数据
     * @param bean 路由详细信息，最终实体封装类
     */
    private final boolean checkRouterPath(RouterBean bean) {
        String group = bean.getGroup(); //  同学们，一定要记住： "app"   "login"
        String path = bean.getPath();   //  同学们，一定要记住： "/app/MainActivity"

        //必须“/”开头
        if (ProcessorUtils.isEmpty(path) || !path.startsWith("/")) {
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter error,Must start with ’/‘");
            return false;
        }

        // 比如开发者代码为：path = "/MainActivity"，最后一个 / 符号必然在字符串第1位
        if (path.lastIndexOf("/") == 0) {
            // 架构师定义规范，让开发者遵循
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter error,example : /app/MainActivity");
            return false;
        }

        // 从第一个 / 到第二个 / 中间截取，如：/app/MainActivity 截取出 app,order,personal 作为group
        String finalGroup = path.substring(1, path.indexOf("/", 1));

        // app,order,personal == options

        // @ARouter注解中的group有赋值情况
        if (!ProcessorUtils.isEmpty(group) && !group.equals(options)) {
            // 架构师定义规范，让开发者遵循
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter error,Package and group names must match");
            return false;
        } else {
            bean.setGroup(finalGroup);
        }

        // 如果真的返回ture   RouterBean.group  xxxxx 赋值成功 没有问题
        return true;
    }
}
