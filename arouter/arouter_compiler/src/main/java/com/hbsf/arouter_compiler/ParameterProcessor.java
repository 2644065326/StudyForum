package com.hbsf.arouter_compiler;

import com.google.auto.service.AutoService;
import com.hbsf.arouter_annotation.Parameter;
import com.hbsf.arouter_compiler.factory.ParameterFactory;
import com.hbsf.arouter_compiler.util.ProcessorConfig;
import com.hbsf.arouter_compiler.util.ProcessorUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;


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
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
@SupportedAnnotationTypes({ProcessorConfig.PARAMETER_PACKAGE})
@SupportedSourceVersion(SourceVersion.RELEASE_7)

public class ParameterProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Types typeUtils;
    private Messager messager;
    private Filer filer;  // 生成器


    private Map<TypeElement, List<Element>> tempParameterMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementUtils = processingEnvironment.getElementUtils();
        typeUtils = processingEnvironment.getTypeUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set.isEmpty()) {
            messager.printMessage(Diagnostic.Kind.NOTE, "并没有发现 被@ARouter注解的地方呀");
            return false; // 没有机会处理
        }

        // 扫描的时候，看那些地方使用到了@Parameter注解
        if (!ProcessorUtils.isEmpty(set)) {

            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Parameter.class);

            if (!ProcessorUtils.isEmpty(elements)) {
                for (Element element : elements) { // element == name， sex,  age
                    TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();

                    if (tempParameterMap.containsKey(enclosingElement)) {
                        tempParameterMap.get(enclosingElement).add(element);
                    } else {
                        List<Element> fields = new ArrayList<>();
                        fields.add(element);
                        tempParameterMap.put(enclosingElement, fields); // 加入缓存
                    }
                }

                // 类文件
                if (ProcessorUtils.isEmpty(tempParameterMap)) return true;

                TypeElement activityType = elementUtils.getTypeElement(ProcessorConfig.ACTIVITY_PACKAGE);
                TypeElement parameterType = elementUtils.getTypeElement(ProcessorConfig.AROUTER_AIP_PARAMETER_GET);

                // 生成方法
                // Object targetParameter
                ParameterSpec parameterSpec = ParameterSpec.builder(TypeName.OBJECT, ProcessorConfig.PARAMETER_NAME).build();

                // 循环遍历 缓存tempParameterMap
                // 可能很多地方都使用了 @Parameter注解，那么就需要去遍历 仓库
                for (Map.Entry<TypeElement, List<Element>> entry : tempParameterMap.entrySet()) {

                    TypeElement typeElement = entry.getKey();

                    // 非Activity直接报错
                    // 如果类名的类型和Activity类型不匹配
                    if (!typeUtils.isSubtype(typeElement.asType(), activityType.asType())) {
                        throw new RuntimeException("@Parameter注解目前仅限用于Activity类之上");
                    }

                    // 是Activity
                    // 获取类名 == Personal_MainActivity
                    ClassName className = ClassName.get(typeElement);

                    // 方法生成成功
                    ParameterFactory factory = new ParameterFactory.Builder(parameterSpec)
                            .setMessager(messager)
                            .setElementUtils(elementUtils)
                            .setTypeUtils(typeUtils)
                            .setClassName(className)
                            .build();

                    // Personal_MainActivity t = (Personal_MainActivity) targetParameter;
                    factory.addFirstStatement();

                    // 难点 多行
                    for (Element element : entry.getValue()) {
                        factory.buildStatement(element);
                    }

                    // 最终生成的类文件名（类名$$Parameter） 例如：Personal_MainActivity$$Parameter
                    String finalClassName = typeElement.getSimpleName() + ProcessorConfig.PARAMETER_FILE_NAME;
                    messager.printMessage(Diagnostic.Kind.NOTE, "APT生成获取参数类文件：" +
                            className.packageName() + "." + finalClassName);

                    // 开始生成文件，例如：PersonalMainActivity$$Parameter
                    try {
                        JavaFile.builder(className.packageName(),
                                TypeSpec.classBuilder(finalClassName)
                                        .addSuperinterface(ClassName.get(parameterType)) //  implements ParameterGet 实现ParameterLoad接口
                                        .addModifiers(Modifier.PUBLIC)
                                        .addMethod(factory.build())
                                        .build())
                                .build()
                                .writeTo(filer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }
}
