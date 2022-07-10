package com.spring5.aop_annotation;

import org.springframework.stereotype.Component;

//目标类
@Component(value = "user")
public class User {
    public void add() {
        System.out.println("add...");
    }
}
