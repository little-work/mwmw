package com.lilin.mwmw.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MySelector implements ImportSelector {


    /**
     * 返回一个String数组  里面放需要注册到容器中类的全类名
     * @param annotationMetadata   当前类的注解信息
     * @return
     */
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"com.lilin.mwmw.bo.Login"};
    }
}
