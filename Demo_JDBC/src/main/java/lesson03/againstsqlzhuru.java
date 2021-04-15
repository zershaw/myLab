package lesson03;

import lesson02.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class againstsqlzhuru {
    public static void main(String[] args) {
        login("陈泽潇","123456");
        //login("'' or 1=1","'' or 1=1");//sql注入
    }

    //登录业务
    public static void login(String username,String password){
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet query = null;

        try {
            conn = JdbcUtils.getConnection(); //获取数据库连接

            String sql="SELECT * FROM users WHERE `NAME`= ? AND `PASSWORD`=?";

            st = conn.prepareStatement(sql);
            st.setString(1,username);
            st.setString(2,password);
            query = st.executeQuery();
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
