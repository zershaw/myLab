package com.example.springboot02;

import com.example.springboot02.pojo.Person;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Springboot02Application implements InitializingBean {
    @Autowired
    Person person;
    public static void main(String[] args) {
        SpringApplication.run(Springboot02Application.class, args);
    }
    @Override
    public void afterPropertiesSet() {
        System.out.println(person.getName());
        System.out.println(person.getDog().toString());
    }

}
