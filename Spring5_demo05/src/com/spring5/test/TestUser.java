package com.spring5.test;

import com.spring5.pojo.User;
import com.spring5.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;


public class TestUser {
    @Test
    public void testJDBCTemplate() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean01.xml");
        BookService bookService = applicationContext.getBean("bookService", BookService.class);
//        int id = 1;
//        bookService.deleteUser(id);
//        int count = bookService.findCount();
//        System.out.println(count);
//        int id = 1;
//        List<User> all = bookService.findAll();
//        for (User user : all) {
//            System.out.println(user);
//        }
        List<Object[]> batchArgs = new ArrayList<>();
        Object[] o1 = {null, "java", "a"};
        Object[] o2 = {null, "c++", "aa"};
        Object[] o3 = {null, "python", "aaa"};
        batchArgs.add(o1);
        batchArgs.add(o2);
        batchArgs.add(o3);

        bookService.batchAddUser(batchArgs);

    }
}
