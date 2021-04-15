package lesson03;

import lesson02.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;


public class TestInsert {
    public static void main(String[] args) throws SQLException {
        Connection conn=null;
        PreparedStatement st=null;
        try {
            conn = JdbcUtils.getConnection(); //获取数据库链接

            //区别：使用？占位符来代替参数
            String sql="INSERT INTO users(id,`NAME`,`PASSWORD`,`email`,`birthday`) VALUES (?,?,?,?,?)";
            //预编译SQL，先写sql，然后不执行
            st= conn.prepareStatement(sql);

            //手动给参数赋值
            st.setInt(1,7);
            st.setObject(2,"张三");
            st.setObject(3,"123456");
            st.setObject(4,"2333@qq.com");
            //注意点：sql.Date    数据库    java.sql.Date
            //       util.Date   Java    new Date().getTime()
            st.setDate(5,new java.sql.Date(new Date().getTime()));

            int i = st.executeUpdate();//executeUpdate的参数为空

            if(i>0) System.out.println("插入成功");

        }catch (Exception e){
            e.printStackTrace();

        }finally {
            JdbcUtils.release(conn,st,null);
        }
    }
}
