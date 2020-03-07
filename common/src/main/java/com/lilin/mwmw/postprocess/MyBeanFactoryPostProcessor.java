package com.lilin.mwmw.postprocess;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * beanFactory的后置处理器  在BeanFactory标准初始化之后调用  所有的Bean已经定义好加载进beanFacory
 * 但是Bean的实例还没有创建   来定制和修改BeanFactory中的内容
 *
 *
 * incoker BeanFactoryPostProcessor(BeanFactory)
 *          1、直接在BeanFactory中找到所有BeanFactoryPostProcessor组件并执行他们的方法
 *          2、在初始化创建其他组件之前
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory clb) throws BeansException {
        /**
         * 从doc中可以读到，BeanFactoryPostProcessor的主要作用是让你能接触到
         * bean definitions，对bean definitions进行一定hack，但是也仅此而已了。
         * 绝对不允许在BeanFactoryPostProcessor中触发到bean的实例化
         *
         * 但是从来没有bean实例。这样做可能会导致过早实例化bean，破坏容器并导致意外的副作用
         *
         * 可以看到的是postProcessBeanFactory(beanFactory);
         * 首先invoke了容器中的BeanFactoryPostProcessor实现类，
         * 其中当然就包括PrematureBeanFactoryPostProcessor，
         * 此时通过clb.getBean("postBean");触发了bean提前实例化。
         * 按理说，bean提前实例化也应该没问题的，
         * aBean也应该是能够被注入的呀！那为啥最终不是这个结果呢。
         * 让我们研究下@Resource @AutoWired这种注解是如何注入依赖的，
         * 如何起作用的就明白了。
         * @AutoWired起作用依赖AutowiredAnnotationBeanPostProcessor,
         * @Resource依赖CommonAnnotationBeanPostProcessor，这俩都是BeanPostProcessor的实现。
         * 那BeanPostProcessors在何处被spring invoke呢，
         * 参见registerBeanPostProcessors(beanFactory);在postProcessBeanFactory(beanFactory);
         * 后面被调用，也就是说BBean被触发提前初始化的时候，AutowiredAnnotationBeanPostProcessor还没有被注册
         * 自然也不会被执行到，自然ABean=null。
         *
         *
         * 但是通过配置文件的方式配置bean就不会  因为解析配置文件的时候 bean的依赖关系已经被解析成了beanDefinition对象
         * 就按照这个对象进行创建
         *
         *
         * 1。eg
         * PostBean pb=(PostBean)clb.getBean("postBean");
         */
        for(String str:clb.getBeanDefinitionNames()){
            System.out.println("已经定义好的BeanDefinition有："+str);
        }
    }
}
