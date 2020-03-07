package com.lilin.mwmw.aopChain;

import java.lang.reflect.Method;

/**
 * 返回通知方法
 */
public class AspectJAfterReturningAdvice extends AbstractAspectJAdvice{

    public AspectJAfterReturningAdvice(Method method, Object adviceObject) {
        super(method, adviceObject);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable{
        Object o = invocation.proceed();
        this.invokeAdviceMethod();
        return o;
    }
}
