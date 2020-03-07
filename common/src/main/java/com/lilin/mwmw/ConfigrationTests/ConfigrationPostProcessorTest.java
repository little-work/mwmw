package com.lilin.mwmw.ConfigrationTests;

import com.lilin.mwmw.applicationContextAware.SpringApplicationContext;
import com.lilin.mwmw.configrations.ConfigrationPostProcessor;
import com.lilin.mwmw.postprocess.PostBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigrationPostProcessorTest {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext a = new AnnotationConfigApplicationContext(ConfigrationPostProcessor.class);

        SpringApplicationContext context=(SpringApplicationContext) a.getBean("springApplicationContext");
        PostBean pb=(PostBean)context.getBeanByClass(PostBean.class);
        System.out.println("通过实现接口获取spring上下文中所有的Bean"+pb);
        System.out.println("此时这个bean已经可以正常被使用了:"+pb.getDIBeanStr());
        /*//可以自定义发布我们的事件
        a.publishEvent(new ApplicationEvent("自定义事件发布") {
        });
        a.close();*/
    }
}
