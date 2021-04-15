package com.sawyer.dao;

import com.sawyer.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserDAO {
    /**
     * 保存被注册的用户
     * @param user
     */
    void save(User user);


    /**
     * 用户登录
     * @param username,password
     */
    //在mybatis中传递多个参数需要参数的绑定
    User login(@Param("username") String username, @Param("password") String password);
}
