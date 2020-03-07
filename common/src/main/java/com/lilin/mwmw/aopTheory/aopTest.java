package com.lilin.mwmw.aopTheory;

public class aopTest {

    public static void main(String[] args) {
        //代理工厂
        ProxyFactory proxyFactory = new ProxyFactory();
        //增强目标方法
        TargetClass targetClass = (TargetClass) proxyFactory.crateProxy(TargetClass.class);
        targetClass.say();
    }
}
