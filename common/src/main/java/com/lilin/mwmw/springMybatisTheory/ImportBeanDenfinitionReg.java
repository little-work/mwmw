package com.lilin.mwmw.springMybatisTheory;

import com.lilin.mwmw.utils.MyFileUtils;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Set;

@Component
public class ImportBeanDenfinitionReg implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        //获取注解里面所有的属性
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(MyCompanscan.class.getName()));

        //获取注解里面basePackages的值  并进行遍历
        for (String pkg : annoAttrs.getStringArray("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                //获取包下面所有类的全名
                Set<String> set = MyFileUtils.getClassName(pkg);
                //遍历包下面所有的类
                for (String classFullName : set) {
                    BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MyMapperFactoryBean.class);
                    //去创建你的意图
                    AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
                    //这里的意图是给MyMapperFactoryBean这个类的构造方法传一个参数
                    beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(classFullName);
                    //eq:SelectDao
                    String upBeanName=classFullName.replace(pkg+".","");
                    String toLower=upBeanName.substring(0,1).toLowerCase();
                    String beanName=toLower+upBeanName.substring(1,upBeanName.length());
                    System.out.println(beanName+"--will be  creted");
                    //registry就是根据我们的意图去创建这个Bean
                    registry.registerBeanDefinition(beanName, beanDefinition);
                }
            }
        }
    }
}
