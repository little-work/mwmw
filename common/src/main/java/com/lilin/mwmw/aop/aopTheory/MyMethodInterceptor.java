package com.lilin.mwmw.aop.aopTheory;

import com.lilin.mwmw.aop.aopChain.*;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MyMethodInterceptor implements MethodInterceptor {


    //前置方法类
    private AspectJBeforeAdvice beforeAdvice = null;
    //后置方法类
    private AspectJAfterReturningAdvice afterReturningAdvice = null;
    //切面类
    private TransactionManager tx;


    public void setUp() throws Exception {
        //创建切面类
        tx = new TransactionManager();
        //创建前置方法
        beforeAdvice = new AspectJBeforeAdvice(TransactionManager.class.getMethod("start"), tx);
        //创建返回方法
        afterReturningAdvice = new AspectJAfterReturningAdvice(TransactionManager.class.getMethod("commit"), tx);
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        Object returnValue;
        setUp();
        //添加通知方法到List中
        List<com.lilin.mwmw.aop.aopChain.MethodInterceptor> interceptorList = new ArrayList<>();
        interceptorList.add(beforeAdvice);
        interceptorList.add(afterReturningAdvice);
        if (true) {
            ReflectiveMethodInvocation reflectiveMethodInvocation=
                    new ReflectiveMethodInvocation(o, method, interceptorList, objects,methodProxy);

            returnValue=reflectiveMethodInvocation.proceed();
        } else {
            System.out.println("======插入前置通知======");
            //方法的返回值  无返回值的时候为null
            returnValue = methodProxy.invokeSuper(o, objects);
            System.out.println("======插入后者通知======");
        }
        return returnValue;
    }


}
