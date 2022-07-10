package com.spring5.aop_annotation;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
public class PersonProxy {
    @Before(value = "execution(* com.spring5.aop_annotation.User.add(..))")
    public void before() {
        System.out.println("Person before...");
    }
}
