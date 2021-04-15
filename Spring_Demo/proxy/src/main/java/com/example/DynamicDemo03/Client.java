package com.example.DynamicDemo03;

public class Client {
    public static void main(String[] args) {
        //真实角色
        Host host = new Host();
        //代理实例的调用处理程序
        ProxyInvocationHandler pih = new ProxyInvocationHandler();
        pih.setRent(host); //将真实角色放置进去！实现Rent这个接口
        Rent proxy = (Rent)pih.getProxy(); //动态生成对应的代理类！Object强转成Rent类
        proxy.rent();//代理类也实现rent
    }
}
