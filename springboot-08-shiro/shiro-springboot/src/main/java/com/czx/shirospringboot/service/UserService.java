package com.czx.shirospringboot.service;


import com.czx.shirospringboot.pojo.User;

public interface UserService {
    User queryUserByName(String name);
}

