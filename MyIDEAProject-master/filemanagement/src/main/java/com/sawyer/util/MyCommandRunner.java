package com.sawyer.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyCommandRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("应用已经准备就绪 ... 启动浏览器并自动加载指定的页面 ... ");
        try {
            Runtime.getRuntime().exec("cmd   /c   start   http://localhost:8080/files/index");//指定自己项目的路径
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
