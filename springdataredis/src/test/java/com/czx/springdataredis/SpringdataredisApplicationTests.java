package com.czx.springdataredis;

import com.czx.pojo.People;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class SpringdataredisApplicationTests {
    @Autowired
    //private RedisTemplate<String,String> redisTemplate;
    // 无泛型版
     private RedisTemplate<String,Object> redisTemplate;
    @Test
    void contextLoads() {

        People people = new People();
        people.setId(1);
        people.setName("张三");
        redisTemplate.opsForValue().set("people",people);
    }

    @Test
    void get(){
        String name = redisTemplate.opsForValue().get("name").toString();
        System.out.println(name);;
    }
    @Test
    void get2(){
        People people = (People)redisTemplate.opsForValue().get("people");
        System.out.println(people);
    }
//    @Test
//    void set(){
//        redisTemplate.opsForValue().set("wufanxing","jieguo",20, TimeUnit.SECONDS);
//    }

}
