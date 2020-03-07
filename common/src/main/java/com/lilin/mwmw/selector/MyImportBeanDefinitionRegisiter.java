package com.lilin.mwmw.selector;

import com.lilin.mwmw.service.impl.UserServiceConsumeImpl;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegisiter  implements ImportBeanDefinitionRegistrar {


    /**
     *
     * @param annotationMetadata  当前类的注解信息
     * @param beanDefinitionRegistry  手工注册类
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {

        boolean returnStringController = beanDefinitionRegistry.containsBeanDefinition("returnStringController");
        if(returnStringController){
            System.out.println("自己定义规则将Bena注册到Spring容器中");
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(UserServiceConsumeImpl.class);
            beanDefinitionRegistry.registerBeanDefinition("userServiceConsumeImpl",rootBeanDefinition);
        }

    }
}
