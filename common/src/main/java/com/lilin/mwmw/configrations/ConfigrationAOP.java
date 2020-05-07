package com.lilin.mwmw.configrations;


import com.lilin.mwmw.aop.aopApply.MyAspects;
import com.lilin.mwmw.aop.aopApply.TargetMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


/**
 * AOP 指在程序运行的时候 动态的将某段代码切入到指定位置运行
 */
@EnableAspectJAutoProxy   //开启基于注解的AOP模式
//@Configuration
public class ConfigrationAOP {


    //spring对@Configuration注解做特殊处理   给容器中加组件的方法 都是只是从容器中找组件
    @Bean("MyAspects")
    public MyAspects getMyAspects() {
        return new MyAspects();
    }

    @Bean("targetMethod")
    public TargetMethod getTargetMethod() {
        return new TargetMethod();
    }


}
