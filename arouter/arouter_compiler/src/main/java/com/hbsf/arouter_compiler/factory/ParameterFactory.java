package com.hbsf.arouter_compiler.factory;

import com.hbsf.arouter_annotation.Parameter;
import com.hbsf.arouter_compiler.util.ProcessorConfig;
import com.hbsf.arouter_compiler.util.ProcessorUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;


import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

/*
   目的 生成以下代码：
        @Override
        public void getParameter(Object targetParameter) {
              Personal_MainActivity t = (Personal_MainActivity) targetParameter;
              t.name = t.getIntent().getStringExtra("name");
              t.sex = t.getIntent().getStringExtra("sex");
        }
 */
public class ParameterFactory {


    // 方法的构建
    private MethodSpec.Builder method;

    // 类名，如：MainActivity  /  Personal_MainActivity
    private ClassName className;

    // Messager用来报告错误，警告和其他提示信息
    private Messager messager;

    // 不想用户使用此构造函数，必须使用Builder设计模式
    private ParameterFactory(Builder builder) {
        this.messager = builder.messager;
        this.className = builder.className;
        // 生成此方法
        // 通过方法参数体构建方法体：public void getParameter(Object target) {
        method = MethodSpec.methodBuilder(ProcessorConfig.PARAMETER_METHOD_NAME)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(builder.parameterSpec);
    }

    /** 只有一行
     * Personal_MainActivity t = (Personal_MainActivity) targetParameter;
     */
    public void addFirstStatement() {
        method.addStatement("$T t = ($T) " + ProcessorConfig.PARAMETER_NAME, className, className);
    }

    public MethodSpec build() {
        return method.build();
    }

    /** 多行 循环 复杂
     * 构建方体内容，如：t.s = t.getIntent.getStringExtra("s");
     * @param element 被注解的属性元素
     */
    public void buildStatement(Element element) {
        // 遍历注解的属性节点 生成函数体
        TypeMirror typeMirror = element.asType();

        // 获取 TypeKind 枚举类型的序列号
        int type = typeMirror.getKind().ordinal();

        // 获取属性名  name  age  sex
        String fieldName = element.getSimpleName().toString();

        // 获取注解的值
        String annotationValue = element.getAnnotation(Parameter.class).name();

        // 配合： t.age = t.getIntent().getBooleanExtra("age", t.age ==  9);
        // 判断注解的值为空的情况下的处理（注解中有name值就用注解值）
        annotationValue = ProcessorUtils.isEmpty(annotationValue) ? fieldName : annotationValue;

        // TODO 最终拼接的前缀：
        String finalValue = "t." + fieldName;

        // t.s = t.getIntent().
        // TODO t.name = t.getIntent().getStringExtra("name");
        String methodContent = finalValue + " = t.getIntent().";

        // TypeKind 枚举类型不包含String
        if (type == TypeKind.INT.ordinal()) {
            // t.s = t.getIntent().getIntExtra("age", t.age);
            methodContent += "getIntExtra($S, " + finalValue + ")";  // 有默认值
        } else if (type == TypeKind.BOOLEAN.ordinal()) {
            // t.s = t.getIntent().getBooleanExtra("isSuccess", t.age);
            methodContent += "getBooleanExtra($S, " + finalValue + ")";  // 有默认值
        } else  { // String 类型，没有序列号的提供 需要我们自己完成
            // t.s = t.getIntent.getStringExtra("s");
            // typeMirror.toString() java.lang.String
            if (typeMirror.toString().equalsIgnoreCase(ProcessorConfig.STRING)) {
                // String类型
                methodContent += "getStringExtra($S)"; // 没有默认值
            }
        }

        // 健壮代码
        if (methodContent.endsWith(")")) { // 抱歉  全部的 getBooleanExtra  getIntExtra   getStringExtra
            // 参数二 9 赋值进去了
            // t.age = t.getIntent().getBooleanExtra("age", t.age ==  9);
            method.addStatement(methodContent, annotationValue);
        } else {
            messager.printMessage(Diagnostic.Kind.ERROR, "目前暂支持String、int、boolean传参");
        }
    }

    /**
     * 为了完成Builder构建者设计模式
     */
    public static class Builder {

        // Messager用来报告错误，警告和其他提示信息
        private Messager messager;

        // 类名，如：MainActivity
        private ClassName className;

        // 方法参数体
        private ParameterSpec parameterSpec;

        public Builder(ParameterSpec parameterSpec) {
            this.parameterSpec = parameterSpec;
        }

        public Builder setMessager(Messager messager) {
            this.messager = messager;
            return this;
        }

        public Builder setClassName(ClassName className) {
            this.className = className;
            return this;
        }

        public ParameterFactory build() {
            if (parameterSpec == null) {
                throw new IllegalArgumentException("parameterSpec方法参数体为空");
            }

            if (className == null) {
                throw new IllegalArgumentException("方法内容中的className为空");
            }

            if (messager == null) {
                throw new IllegalArgumentException("messager为空，Messager用来报告错误、警告和其他提示信息");
            }

            return new ParameterFactory(this);
        }
    }
}
