package com.example.springboot02.Scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {
//    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//
//    @Scheduled(fixedRate = 10000)
//    public void timerRate() {
//        System.out.println(dateFormat.format(new Date()));
//    }
//
//    //第一次延遲1秒執行，當執行完後2秒再執行
//    @Scheduled(initialDelay = 1000, fixedDelay = 2000)
//    public void timerInit() {
//        System.out.println("init : "+dateFormat.format(new Date()));
//    }
//
//    //每天21點41分50秒執行
//    @Scheduled(cron = "50 41 21 * * ?")
//    public void timerCron() {
//        System.out.println("current time : "+ dateFormat.format(new Date()));
//    }
}

