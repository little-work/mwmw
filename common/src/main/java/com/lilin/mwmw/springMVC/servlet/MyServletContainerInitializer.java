package com.lilin.mwmw.springMVC.servlet;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.util.EnumSet;
import java.util.Set;


/**
 * 容器在启动的时候会扫描当前应用每一个jar包
 * META-INF/services/javax.servlet.ServletContainerInitializer指定实现类
 * 启动并运行这个实现类的方法，传入感兴趣的类型
 */

@HandlesTypes(HelloServlet.class)
public class MyServletContainerInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {

        //这里的set就是HandlesTypes注解传进来的HelloServlet类型的类集合
        for(Class<?> ff:set){
            System.out.println(ff);
        }

        //添加servlet
        ServletRegistration.Dynamic servlet=servletContext.addServlet("loginServlet",new LoginServlet());
        servlet.addMapping("/hello");
        //添加监听器
        servletContext.addListener(LoginListener.class);

        //添加filter
        FilterRegistration.Dynamic filter=servletContext.addFilter("loginFilter",LoginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");


    }
}
