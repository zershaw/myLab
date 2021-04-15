package com.example.springboot05mybatis.mapper;

import com.example.springboot05mybatis.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

//这个注解表示这是一个mybatis的 mapper类：Dao
@Mapper
@Repository
public interface UserMapper {
    List<User> queryUserList();
    //根据id选择用户
    User queryUserById(int id);
    //添加一个用户
    int addUser(User user);
    
    //修改一个用户
    int updateUser(User user);
    //根据id删除用户
    int deleteUser(int iid);
}
