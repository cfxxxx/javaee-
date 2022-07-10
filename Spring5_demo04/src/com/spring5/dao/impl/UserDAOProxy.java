package com.spring5.dao.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UserDAOProxy implements InvocationHandler {
    private Object object;

    public UserDAOProxy(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("方法之前被执行...");

        Object res = method.invoke(object, args);

        System.out.println("方法之后被执行...");
        return res;
    }
}
