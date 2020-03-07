package com.lilin.mwmw.postprocess;

import com.lilin.mwmw.bo.Login;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.stereotype.Component;


/**
 * postProcessBeanDefinitionRegistry()这个方法先执行
 *
 * postProcessBeanFactory()这个方法第二执行
 */
//@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {


    /**
     * beanDefinitionRegistry  Bean的定义信息保存中心  以后BeanFactory就按照beanDefinitionRegistry去创建Bean
     *
     * @param beanDefinitionRegistry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {

        System.out.println("postProcessBeanDefinitionRegistry..数量"+beanDefinitionRegistry.getBeanDefinitionCount());
        //RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Login.class);
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Login.class).getBeanDefinition();
        beanDefinitionRegistry.registerBeanDefinition("myCustBeanLogin",beanDefinition);
    }


    //相当于执行Bean工厂  开始根绝定义初始化创建Bean
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("MyBeanDefinitionRegistryPostProcessor..数量"+
                configurableListableBeanFactory.getBeanDefinitionCount());
    }
}
