package com.spring5.service;

import com.spring5.dao.UserDAO;
import com.spring5.dao.impl.UserDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

//在注解里面value属性值可以省略不写，
//默认值是类名称，首字母小写
//UserService --> userService
//@Component(value = "userService")
@Service
public class UserService {
    //不需要添加set方法
    //添加注入属性的注解
    //@Autowired //根据类型进行注入
    //@Qualifier(value = "userDAOImpl")

    //@Resource //默认根据名称进行注入
    @Resource(type = UserDAOImpl.class) //按照类型注入
    private UserDAO userDAO;

    @Value(value = "小明")
    private String uname;

    public void add() {
        System.out.println("service add...");
        userDAO.add();
        System.out.println("uname = " + uname);
    }
}
