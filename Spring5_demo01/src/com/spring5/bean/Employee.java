package com.spring5.bean;

//员工类
public class Employee {
    private String ename;
    private String gender;

    //员工属于某一个部门，适用对象形式表示
    private Dept dept;

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
