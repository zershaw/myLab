package com.example.springboot05mybatis.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //代表set和get
@NoArgsConstructor
@AllArgsConstructor

public class User {
    private int id;
    private String name;
    private String pwd;
}
