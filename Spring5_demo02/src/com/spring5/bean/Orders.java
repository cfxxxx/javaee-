package com.spring5.bean;

public class Orders {
    private String oname;

    public Orders() {
        System.out.println("无参构造器被调用...");
    }

    public void setOname(String oname) {
        System.out.println("set方法被调用...");
        this.oname = oname;
    }

    //创建执行的初始化方法
    public void initMethod() {
        System.out.println("调用初始化方法...");
    }

    //创建执行的销毁方法
    public void destroyMethod() {
        System.out.println("调用销毁方法...");
    }
}
