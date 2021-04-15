package com.example.ComponentScanTest;

import com.example.config.DemoConfig;
import com.example.pojo.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MyTest {

    public static void main(String[] args) {
        //ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(DemoConfig.class);
        User user = (User) context.getBean("user");
        System.out.println(user.toString());
        System.out.println(user.getCat().name);
        user.getDog().shout();
    }
}