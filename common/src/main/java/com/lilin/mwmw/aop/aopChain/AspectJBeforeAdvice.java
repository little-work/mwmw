package com.lilin.mwmw.aop.aopChain;



import java.lang.reflect.Method;

/**
 * 前置通知方法
 */
public class AspectJBeforeAdvice extends AbstractAspectJAdvice{


    //构造方法调用父类的构造方法
    public AspectJBeforeAdvice(Method method, Object adviceObject) {
        super(method, adviceObject);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable{
        //调用父类的
        this.invokeAdviceMethod();
        Object o = invocation.proceed();
        return o;
    }
}
