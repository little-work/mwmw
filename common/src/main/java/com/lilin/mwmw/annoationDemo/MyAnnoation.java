package com.lilin.mwmw.annoationDemo;


import java.lang.annotation.*;

@Documented //说明该注解将被包含在javadoc中
@Target({ElementType.CONSTRUCTOR,
        ElementType.METHOD,
        ElementType.PARAMETER,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)  //注解的保留位置：运行时存在
public @interface MyAnnoation {

    int max();
    int min();
    String describe();
}
