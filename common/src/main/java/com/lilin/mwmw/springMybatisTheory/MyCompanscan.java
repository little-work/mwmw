package com.lilin.mwmw.springMybatisTheory;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Import(ImportBeanDenfinitionReg.class)
public @interface MyCompanscan {

    String[] basePackages() default {};
}
