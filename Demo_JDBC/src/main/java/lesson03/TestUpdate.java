package lesson03;

import lesson02.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class TestUpdate {
    public static void main(String[] args) throws SQLException {
        Connection conn=null;
        PreparedStatement st=null;
        try {
            conn = JdbcUtils.getConnection(); //获取数据库链接

            //区别：使用？占位符来代替参数
            String sql="update users set `NAME` =? where id = ?;";
            //预编译SQL，先写sql，然后不执行
            st= conn.prepareStatement(sql);

            //手动给参数赋值
            st.setString(1,"詹韩峰");
            st.setInt(2,1);


            int i = st.executeUpdate();//executeUpdate的参数为空

            if(i>0) System.out.println("更新成功");

        }catch (Exception e){
            e.printStackTrace();

        }finally {
            JdbcUtils.release(conn,st,null);
        }
    }
}
