package com.example.springboot04data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class JDBCController {
    @Autowired
    JdbcTemplate jdbcTemplate;//jdbc增删改查模板

    //查询数据库的所有信息
    //没有实体类，数据库中的东西怎么获取》map
    @GetMapping("/userList")
    public List<Map<String,Object>> userList(){
        String sql = "select * from users";
        List<Map<String,Object>> list_maps = jdbcTemplate.queryForList(sql);
        return list_maps;
    }
    @GetMapping("/addUser")
    public String addUser(){
        String sql = "insert into jdbcstudy.users(NAME,PASSWORD) values('小猪','1234567')";//id自动递增
        jdbcTemplate.update(sql);
        return "update-ok";
    }
    @GetMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id")int id){
        String sql = "update jdbcstudy.users  set NAME=?,PASSWORD=?  where id="+id;//id自动递增
        //封装
        Object[] objects = new Object[2];
        objects[0] = "更新";
        objects[1] = "zzzzzzzzz";
        jdbcTemplate.update(sql,objects);
        return "update-ok";
    }
}
