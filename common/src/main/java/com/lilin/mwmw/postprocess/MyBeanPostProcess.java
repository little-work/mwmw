package com.lilin.mwmw.postprocess;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Bean的后置处理器：Bean创建对象初始化前后进行拦截操作
 * 该接口我们也叫后置处理器，作用是在Bean对象在实例化和依赖注入完毕后，在显示调用初始化方法的前后添加我们自己的逻辑
 *
 * @AutoWired起作用依赖AutowiredAnnotationBeanPostProcessor,
 * @Resource依赖CommonAnnotationBeanPostProcessor，这俩都是BeanPostProcessor的实现。
 *
 *
 * 我们也可以自定义注解  并定义一个beanPostProcess去执行
 *
 *
 *
 * ApplicationContextAware  是通过ApplicationContextAwareProcess这个实现了beanPostProcess接口的类实现的
 *
 */
@Component
public class MyBeanPostProcess implements BeanPostProcessor {

    /**
     * Bean初始化之前调用
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization这个Bean的名字是"+beanName);
        return bean;
    }

    /**
     * Bean初始化之后调用
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization这个Bean的名字是"+beanName);
        if(bean instanceof PostBean){
            PostBean p=(PostBean) bean;
            System.out.println("初始化好了"+((PostBean) bean).getDIBeanStr());
        }

        return bean;
    }
}
