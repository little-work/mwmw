package com.lilin.mwmw.applicationContextAware;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * 当一个类实现了这个接口（ApplicationContextAware）之后，
 * 这个类就可以方便获得ApplicationContext中的所有bean。换句话说，
 * 就是这个类可以直接获取spring配置文件中，所有有引用到的bean对象。
 */
//@Component
public class SpringApplicationContext implements ApplicationContextAware {


    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //当spring容器初始化的时候，会自动的将ApplicationContext注入进来：
        System.out.println("注入springContext");
        this.applicationContext = applicationContext;
    }

    /**
     * 通过类型获得Bean
     *
     * @param clazz
     * @return
     */
    public Object getBeanByClass(Class clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 通过bean的名字获得这个bean
     *
     * @param beanName
     * @return
     */
    public Object getBeanByName(String beanName) {
        return applicationContext.getBean(beanName);
    }
}
