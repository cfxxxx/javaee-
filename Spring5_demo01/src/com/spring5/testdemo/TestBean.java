package com.spring5.testdemo;

import com.spring5.bean.Employee;
import com.spring5.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBean {

    @Test
    public void testService() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean02.xml");
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.add();
    }


    @Test
    public void testEmployee1() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean03.xml");
        Employee employee = applicationContext.getBean("employee", Employee.class);
    }

    @Test
    public void testEmployee2() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean04.xml");
        Employee employee = applicationContext.getBean("employee", Employee.class);
    }
}
