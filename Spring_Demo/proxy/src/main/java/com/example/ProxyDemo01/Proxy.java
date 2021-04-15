package com.example.ProxyDemo01;

import org.springframework.beans.factory.annotation.Autowired;

public class Proxy implements Rent{
    @Autowired
    private Host host;
    public Proxy() { }
    public Proxy(Host host) {
        this.host = host;
    }

    //租房
    public void rent(){
        seeHouse();
        host.rent();
        fare();
    }
    //看房
    public void seeHouse(){
        System.out.println("中介带房客看房");
    }
    //收中介费
    public void fare(){
        System.out.println("中介收中介费");
    }
}
