package com.spring5.dao.impl;

import com.spring5.dao.UserDAO;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class JDKProxy {
    public static void main(String[] args) {
        Class[] interfaces = {UserDAO.class};
        //创建接口实现类代理对象
         UserDAO userDAO = (UserDAO)Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new UserDAOProxy(new UserDAOImpl()));
        int add = userDAO.add(1, 2);
        System.out.println(add);

    }
}
