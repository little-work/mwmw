package com.lilin.mwmw.conditionals;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;


public class MyConditional implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {

        //获取当前环境信息
        Environment environment = conditionContext.getEnvironment();
        //获取IOC使用的BeanFactory
        ConfigurableListableBeanFactory beanFactory = conditionContext.getBeanFactory();
        //获取加载器
        ClassLoader classLoader = conditionContext.getClassLoader();
        //获取Bean的注册类
        BeanDefinitionRegistry registry = conditionContext.getRegistry();
        //判断容器中Bean情况  查看有没有这个Bean
        if(registry.containsBeanDefinition("da")){
            return true;
        }
        return false;
    }
}
