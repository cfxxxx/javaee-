package com.spring5.testdemo;


import com.spring5.config.SpringConfig;
import com.spring5.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring5Demo01 {
    @Test
    public void testService1() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean01.xml");
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.add();
    }

    @Test
    public void testService2() {
        //加载配置类
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.add();
    }
}
