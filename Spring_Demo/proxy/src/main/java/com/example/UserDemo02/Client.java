package com.example.UserDemo02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        //真实业务
        //UserServiceImpl userService = new UserServiceImpl();

        //代理类
        UserServiceProxy proxy  = (UserServiceProxy) context.getBean("UserServiceProxy");
        //UserServiceProxy proxy = new UserServiceProxy();
        //使用代理类实现日志功能！
        proxy.getUserService();

        proxy.add();
    }
}
