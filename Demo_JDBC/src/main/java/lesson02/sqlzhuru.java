package lesson02;

import lesson02.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class sqlzhuru {
    public static void main(String[] args) {
        //login("陈泽潇","123456");
        login("'' or 1=1","123456");//sql注入
    }

    //登录业务
    public static void login(String username,String password){
        Connection conn = null;
        Statement st = null;
        ResultSet query = null;

        try {
            conn = JdbcUtils.getConnection(); //获取数据库连接
            st = conn.createStatement();//获取Sql执行对象

            String sql="SELECT * FROM users WHERE `NAME`= "+"'"+username+"'"+" AND `PASSWORD`="+"'"+password+"'";
            query = st.executeQuery(sql);
            //遍历
            while (query.next()){
                System.out.println(query.getObject("NAME"));
                System.out.println(query.getObject("PASSWORD"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(conn,st, query);
        }
    }
}
