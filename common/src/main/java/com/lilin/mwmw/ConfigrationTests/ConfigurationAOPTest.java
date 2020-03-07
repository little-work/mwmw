package com.lilin.mwmw.ConfigrationTests;

import com.lilin.mwmw.aopApply.TargetMethod;
import com.lilin.mwmw.configrations.ConfigrationAOP;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationAOPTest {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext a = new AnnotationConfigApplicationContext(ConfigrationAOP.class);

        String[] beanNames = a.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println("spring容器中现在有的Bean的名字——" + beanName);
        }


        TargetMethod tm = a.getBean(TargetMethod.class);

        tm.execute();


    }
}
