package com.lilin.mwmw.initbean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

//@Component
@Primary    //Autowired的时候  多个bean首选这个bean
public class InitBean implements InitializingBean, DisposableBean {


    public  InitBean(){
        System.out.println("创建InitBean");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("容器销毁这个bean的时候调用这个方法");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("容器完成属性赋值的时候调用");
    }
}
