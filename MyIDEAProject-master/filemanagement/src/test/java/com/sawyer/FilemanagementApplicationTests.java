package com.sawyer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class FilemanagementApplicationTests {

    @Test
    void test1() throws FileNotFoundException {
        System.out.println(ResourceUtils.getURL("classpath:").getPath() );
    }

    @Test
    void test2(){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()));
    }

}
