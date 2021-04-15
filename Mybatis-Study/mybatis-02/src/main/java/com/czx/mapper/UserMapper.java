package com.czx.mapper;

import com.czx.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<User> getUserList();

    //添加一个用户
    int addUser(User user);

    //根据id查询用户
    User selectUserById(int id);


}
