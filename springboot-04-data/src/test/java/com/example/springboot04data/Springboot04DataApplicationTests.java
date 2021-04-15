package com.example.springboot04data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class Springboot04DataApplicationTests {

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() throws SQLException {
        //查看一下默认的数据源（连接池） class com.zaxxer.hikari.HikariDataSource,类似DBCP，是spring默认的数据源
        System.out.println(dataSource.getClass());

        //获得数据库连接 HikariProxyConnection@786722925 wrapping com.mysql.cj.jdbc.ConnectionImpl@6650a6c
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }

}
