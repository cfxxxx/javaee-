package com.spring5.testdemo;

import com.spring5.Book;
import com.spring5.Orders;
import com.spring5.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring5 {

    @Test
    public void testAdd() {
        //1.加载Spring配置文件
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean01.xml");

        //2.获取配置创建的对象
        User user = applicationContext.getBean("user", User.class);

        user.add();
    }

    @Test
    public void testBook() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean01.xml");
        Book book = applicationContext.getBean("book", Book.class);
    }

    @Test
    public void testOrders() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean01.xml");
        Orders orders = applicationContext.getBean("orders", Orders.class);
    }


}
