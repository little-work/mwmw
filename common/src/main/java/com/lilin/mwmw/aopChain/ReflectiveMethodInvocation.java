package com.lilin.mwmw.aopChain;



import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 实现方法递归实现链式调用。
 */
public class ReflectiveMethodInvocation implements MethodInvocation {


    //目标对象
    private final Object targetObject;
    //目标方法
    private final Method targetMethod;
    //通知方法
    private final List<MethodInterceptor> interceptorList;

    private MethodProxy methodProxy;

    private Object[] objects;

    private int currentInterceptorIndex = -1;


    public ReflectiveMethodInvocation(Object targetObject, Method targetMethod, List<MethodInterceptor> interceptorList, Object[] objects,MethodProxy methodProxy) {
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.interceptorList = interceptorList;
        this.methodProxy=methodProxy;
        this.objects=objects;
    }

    @Override
    public Object proceed() throws Throwable {

        if (this.currentInterceptorIndex == this.interceptorList.size() - 1) {
            return invokeJoinPoint();
        }

        this.currentInterceptorIndex++;

        MethodInterceptor interceptor =
                this.interceptorList.get(this.currentInterceptorIndex);
        return interceptor.invoke(this);
    }

    //调用目标类中的被增强的方法
    private Object invokeJoinPoint() throws Throwable {
        return this.methodProxy.invokeSuper(this.targetObject,objects);
    }


}
