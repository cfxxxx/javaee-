package com.spring5.aop_annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//增强类
@Component
@Aspect
@Order(2)
public class UserProxy {

    //相同切入点抽取
    @Pointcut(value = "execution(* com.spring5.aop_annotation.User.add(..))")
    public void pointDemo() {
    }

    //前置通知
    @Before(value = "pointDemo()")
    public void before() {
        System.out.println("before...");
    }

    //后置通知
    @After(value = "execution(* com.spring5.aop_annotation.User.add(..))")
    public void after() {
        System.out.println("after...");
    }

    @AfterReturning(value = "execution(* com.spring5.aop_annotation.User.add(..))")
    public void afterReturning() {
        System.out.println("afterReturning...");
    }

    //异常通知
    @AfterThrowing(value = "execution(* com.spring5.aop_annotation.User.add(..))")
    public void afterThrowing() {
        System.out.println("afterThrowing...");
    }

    //环绕通知
    @Around(value = "execution(* com.spring5.aop_annotation.User.add(..))")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("aroundBefore...");
        //被增强的方法执行
        proceedingJoinPoint.proceed();
        System.out.println("aroundAfter...");
    }
}
