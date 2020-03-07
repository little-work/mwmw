package com.lilin.mwmw.aopTheory;

import org.springframework.cglib.proxy.Enhancer;

public class ProxyFactory<T> {


    /**
     * 生成cglib代理的工程类
     *
     * @param obj
     * @return
     */
    public Object crateProxy(Class<T> obj) {
        // 通过CGLIB动态代理获取代理对象的过程
        Enhancer enhancer = new Enhancer();
        // 设置enhancer对象的父类
        enhancer.setSuperclass(obj);
        // 设置enhancer的回调对象
        enhancer.setCallback(new MyMethodInterceptor());
        // 创建代理对象
        return enhancer.create();
    }

}
