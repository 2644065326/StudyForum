package com.hbsf.arouter_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // 该注解作用在类之上
@Retention(RetentionPolicy.CLASS) // 要在编译时进行一些预处理操作，注解会在class文件中存在
public @interface ARouter {

    // 详细路由路径（必填），如："/app/MainActivity"
    String path();

    // 路由组名（选填，如果开发者不填写，可以从path中截取出来）
    String group() default "";
}
