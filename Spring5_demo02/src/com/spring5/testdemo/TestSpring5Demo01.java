package com.spring5.testdemo;

import com.spring5.autowire.Employee;
import com.spring5.bean.Orders;
import com.spring5.collectiontype.Book;
import com.spring5.collectiontype.Student;
import com.spring5.collectiontype.Subject;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring5Demo01 {

    @Test
    public void testCollection1() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean01.xml");
        Student student = applicationContext.getBean("student", Student.class);
    }

    @Test
    public void testCollection2() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean02.xml");
        Book book = applicationContext.getBean("book", Book.class);
    }

    @Test
    public void test3() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean03.xml");
        Subject subject = applicationContext.getBean("factorybean", Subject.class);
        System.out.println(subject);
    }

    @Test
    public void test4() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean04.xml");
        Orders orders = applicationContext.getBean("orders", Orders.class);

        //手动让bean实例销毁
        ((ClassPathXmlApplicationContext) applicationContext).close();
    }

    @Test
    public void test5() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean05.xml");
        Employee employee = applicationContext.getBean("employee", Employee.class);
        System.out.println(employee);
    }
}
