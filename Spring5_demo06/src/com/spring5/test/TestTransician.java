package com.spring5.test;

import com.spring5.config.TxConfig;
import com.spring5.service.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jws.soap.SOAPBinding;

public class TestTransician {
    @Test
    public void testAccount() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(TxConfig.class);
        UserService userService = annotationConfigApplicationContext.getBean("userService", UserService.class);
        userService.money();
    }
}
