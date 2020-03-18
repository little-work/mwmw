package com.lilin.mwmw.jdkProxy;

import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.nio.file.Files;

public class jdkProxy {


    public static void main(String[] args) throws IOException {
        UserServiceImpl usim=new UserServiceImpl();
        UserService userService=(UserService) Proxy.newProxyInstance(jdkProxy.class.getClassLoader(),
                new Class[]{UserService.class},(proxy,method,arg1)->{
                    System.out.println("前置方法");
                    Object obj =method.invoke(usim,arg1);
                    System.out.println("后置方法");
                    return obj;
                });
        /**
         * 这个类已经不是原来那个userService，是Proxy.newProxyInstance生成的代理类
         * 这个类继承了Proxy类和实现了Uservice接口，所以jdk代理需要接口，然后调用里面的方法的时候
         * 实际上是调用了InvocationHandler的invoker方法
         */
        userService.getName();
        userService.getAge();
        createProxyClass();
    }



    public static void createProxyClass() throws IOException {
       byte[] bytes=ProxyGenerator.generateProxyClass("UserService$Proxy",new Class[]{UserService.class});

        Files.write(new File("D:\\mwmw\\mwmw\\common\\src\\main\\java\\com\\lilin\\mwmw\\jdkProxy\\UserService$Proxy.class").toPath(),
                bytes);
    }
}
