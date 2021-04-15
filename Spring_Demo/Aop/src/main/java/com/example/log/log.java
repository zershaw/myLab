package com.example.log;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class log implements MethodBeforeAdvice {
    //method : 要执行的目标对象的方法
    //args : 被调用的方法的参数
    //Object : 目标对象
    public void before(Method method, Object[] args, Object o) throws Throwable {
        System.out.println( o.getClass().getName() + "的" + method.getName() + "方法被执行了");
    }
}
