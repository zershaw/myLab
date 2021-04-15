package com.example.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Cat {
    @Value("阿猪")
    public String name;
    public void shout() {
        System.out.println("miao~");
    }
}
