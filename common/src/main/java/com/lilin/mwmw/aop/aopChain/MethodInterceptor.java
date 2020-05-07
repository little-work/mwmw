package com.lilin.mwmw.aop.aopChain;

public interface MethodInterceptor {


    Object invoke(MethodInvocation invocation) throws Throwable;

}
