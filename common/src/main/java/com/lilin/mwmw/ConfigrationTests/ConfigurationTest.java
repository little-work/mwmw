package com.lilin.mwmw.ConfigrationTests;

import com.lilin.mwmw.bo.SpringValue;
import com.lilin.mwmw.configrations.Configration2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class ConfigurationTest {


    public static void main(String[] args) {
         AnnotationConfigApplicationContext a=new AnnotationConfigApplicationContext(Configration2.class);

        String[] beanNames=a.getBeanDefinitionNames();
        for(String beanName:beanNames){
            System.out.println(beanName);
        }

        //通过容器拿到外部配置文件中的值
        ConfigurableEnvironment environment = a.getEnvironment();
        String property = environment.getProperty("spring.application.name");
        System.out.println("环境变量中的键spring.application.name的值是："+property);

        //根据Profile的名字激活某个项目环境配置
        a.getEnvironment().setActiveProfiles("test");

        SpringValue sv = a.getBean(SpringValue.class);
        System.out.println(sv.toString());
        a.close();
    }
}
