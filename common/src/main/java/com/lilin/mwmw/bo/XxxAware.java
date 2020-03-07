package com.lilin.mwmw.bo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class XxxAware implements ApplicationContextAware , BeanNameAware {


    //把spring容器注入到这个类中
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    //将Bean的名字传进来
    @Override
    public void setBeanName(String s) {

    }
}
