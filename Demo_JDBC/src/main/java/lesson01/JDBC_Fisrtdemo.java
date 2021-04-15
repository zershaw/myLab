package lesson01;

import java.sql.*;
import java.util.Collection;

public class JDBC_Fisrtdemo {
    //我的第一个JDBC程序
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //1.加载驱动
        Class.forName("com.mysql.jdbc.Driver");//固定写法，加载驱动

        //2.用户信息和url
        String url = "jdbc:mysql://localhost:3306/jdbcstudy?useUnicode=true&characterEncoding=utf8&useSSL=true";
        String username = "root";
        String password = "123456";
        //3.连接成功，数据库对象 connection代表数据库
        Connection connection = DriverManager.getConnection(url, username, password);

        //4.执行sql的【对象】statement 执行sql对象
        Statement statement = connection.createStatement();
        //5.用sql的对象 去 执行 sql，可能存在的结果,查看返回结果 最好在navicat先试一下
        String sql = "SELECT * FROM users";

        ResultSet resultSet = statement.executeQuery(sql);//返回的【结果集合】

        while (resultSet.next()){
            System.out.println("id=" + resultSet.getObject("id"));
            System.out.println("name=" + resultSet.getObject("name"));
            System.out.println("psw=" + resultSet.getObject("password"));
            System.out.println("email=" + resultSet.getObject("email"));
            System.out.println("birth=" + resultSet.getObject("birthday"));
        }
        //6.释放链接
        resultSet.close();
        statement.close();
        connection.close();
    }
}
