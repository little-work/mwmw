package com.lilin.mwmw.aopApply;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 告诉spring这是一个切面类
 */
@Aspect
@Component
public class MyAspects {


    /**
     * 抽取公共的切入点表达式  可以在本类中使用  也可以在其他类中使用(全限类名)
     */
    @Pointcut("execution(public * com.lilin.mwmw.aopApply.TargetMethod.*(..))")
    public void pointCut() {
    }


    //目标方法之前切入：切入的表达式
    //@Before("public void com.lilin.mwmw.aopApply.TargetMethod.execute()")  只是指定具体的类中的那个方法
    @Before("pointCut()")   //方法中的所有方法
    public void executeBefore(JoinPoint joinPoint) {
        //获取目标方法的参数
        Object[] args=joinPoint.getArgs();
        //获取目标方法的方法名
        String methodName=joinPoint.getSignature().getName();
        System.out.println("方法运行之前");
    }


    //假定使用的是外部的一个切点  使用外部那个切点的全限类名
    @After("com.lilin.mwmw.aopApply.MyAspects.pointCut()")
    public void executeAfter(JoinPoint joinPoint) {
        System.out.println("方法运行之后");
    }


    //当方法正常返回的时候调用
    @AfterReturning(value = "pointCut()")
    public void executeReturning(JoinPoint joinPoint) {
        System.out.println("方法正常返回");
    }

    @AfterThrowing(value = "pointCut()")
    public void executeException(JoinPoint joinPoint) {
        System.out.println("方法报错了");
    }
    @Around(value = "pointCut()")
    public void executeArround(JoinPoint joinPoint) {
        System.out.println("环绕方法");
    }


}
