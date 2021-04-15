package com.czx.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

//作用：用工厂类SQLSessionFactory 来创建 SQLSession
public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            //使用MyBatis获取sqlSessionFactory对象
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 既然有了sqlsessionfactory，顾名思义，我们就可以从中获得sqlsession的实例了
    // sqlsession 完全包含了面向数据库执行SQL命令所需的所有方法
    // 获取SqlSession连接
    public static SqlSession getSession(){
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSessionFactory.openSession();
    }
}
