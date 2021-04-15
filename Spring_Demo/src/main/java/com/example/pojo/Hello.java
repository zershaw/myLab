package com.example.pojo;

public class Hello {
    private String name;

    public Hello() {
        System.out.println("spring调用了无参构造");;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void show(){
        System.out.println("Hello,"+ name );
    }
}
