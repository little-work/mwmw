package com.lilin.mwmw.configrations;


import com.lilin.mwmw.initbean.AnnotationInit;
import com.lilin.mwmw.initbean.InitBean;
import com.lilin.mwmw.postprocess.MyBeanPostProcess;
import com.lilin.mwmw.bo.SpringValue;
import org.springframework.context.annotation.*;

import javax.annotation.Resource;

//@Profile("test")  //可以加载类级别的  这个类中所有的注解可以根据环境控制
//@Configuration
@ComponentScan("com.lilin.mwmw.bo")
//加载外部配置文件  保存到运行的环境变量中
@PropertySource(value = {"classpath:/application.properties"})
public class Configration2 {

    //@Qualifier("initBean")
    //@Autowired    //spring注解
    @Resource     //java注解
    private InitBean initBean;


    //@Profile("test")   //添加了Profile注解的Bean  Spring容器启动的时候时不会添加到容器中的  除非添加了default默认值
    @Bean
    public AnnotationInit getAnnotationInit(){
        return new AnnotationInit();
    }

    @Bean
    public MyBeanPostProcess getMyPostProcess(){
        return new MyBeanPostProcess();
    }

    @Bean
    public SpringValue getSpringValue(){
        return new SpringValue();
    }


}
