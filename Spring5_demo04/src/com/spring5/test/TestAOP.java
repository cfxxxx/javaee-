package com.spring5.test;

import com.spring5.aop_annotation.User;
import com.spring5.aop_annotation.UserProxy;
import com.spring5.aopxml.Book;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAOP {

    @Test
    public void testAop_annotation() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean01.xml");
        User user = applicationContext.getBean("user", User.class);
        user.add();
    }

    @Test
    public void testAop_Xml() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean01.xml");
        Book book = applicationContext.getBean("book", Book.class);
        book.buy();
    }
}
