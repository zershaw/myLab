package com.example.ProxyDemo01;

public class Client {
    public static void main(String[] args) {
        //房东要租房
        Host host = new Host();
        //中介【帮助】房东
        Proxy proxy = new Proxy(host);

        //你去找中介！
        proxy.rent();
    }
}
