package com.lilin.mwmw.aopChain;

public interface MethodInterceptor {


    Object invoke(MethodInvocation invocation) throws Throwable;

}
