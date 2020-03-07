package com.lilin.mwmw.aopChain;

import java.lang.reflect.Method;

public abstract  class AbstractAspectJAdvice implements MethodInterceptor{

    private Method adviceMethod;

    private Object adviceObject;

    /**
     * 子类实例化的时候先实例化这个父类 准备后面调用切面方法
     * @param adviceMethod
     * @param adviceObject
     */
    public AbstractAspectJAdvice(Method adviceMethod, Object adviceObject) {
        this.adviceMethod = adviceMethod;
        this.adviceObject = adviceObject;
    }

    public Method getAdviceMethod() {
        return this.adviceMethod;
    }


    /**
     * 调用切面类的adviceMethod方法
     * @throws Throwable
     */
    public void invokeAdviceMethod() throws Throwable {
        adviceMethod.invoke(adviceObject);
    }
}
