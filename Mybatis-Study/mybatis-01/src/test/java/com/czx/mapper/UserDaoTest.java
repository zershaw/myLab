package com.czx.mapper;

import com.czx.pojo.User;
import com.czx.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoTest {

    @Test
    public void test(){
        //第一步：获得Sqlsession对象
        SqlSession sqlSession = MybatisUtils.getSession();
        //方式一：通过反射，在运行时构造UserMapper类的对象，执行SQL
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = userMapper.getUserList();
        for (User user : userList){
            System.out.println(user);
        }
        sqlSession.close();
    }

    @Test
    public void testAddUser() {
        SqlSession session = MybatisUtils.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = new User(7,"佳丽","zxcvbn");
        int i = mapper.addUser(user);
        System.out.println(i); // 1
        session.commit(); //提交事务,重点!不写的话不会提交到数据库
        session.close();
    }

    @Test
    public void tsetSelectUserById() {
        // 用SqlSession实例来直接执行被映射的SQL语句
        // 通过反射，在运行时构造UserMapper类的对象
        SqlSession session = MybatisUtils.getSession();  //获取SqlSession连接
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = mapper.selectUserById(1);
        System.out.println(user);
        session.close();
    }

    @Test
    public void testSelectUserByMap() {
        // 用SqlSession实例来直接执行被映射的SQL语句
        // 通过反射，在运行时构造UserMapper类的对象
        SqlSession session = MybatisUtils.getSession();  //获取SqlSession连接
        UserMapper mapper = session.getMapper(UserMapper.class);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("username","狂神");
        map.put("pwd","123456");
        User user = mapper.selectUserByMap(map);
        System.out.println(user);
        session.close();
    }

    @Test
    public void testgetUserLike(){
        // 用SqlSession实例来直接执行被映射的SQL语句
        // 通过反射，在运行时构造UserMapper类的对象
        SqlSession session = MybatisUtils.getSession();  //获取SqlSession连接
        UserMapper mapper = session.getMapper(UserMapper.class);
        List<User> userList = mapper.getUserLike("%丽%","zxcvbn");
        for (User user : userList) {
            System.out.println(user);
        }
        session.close();
    }
}
