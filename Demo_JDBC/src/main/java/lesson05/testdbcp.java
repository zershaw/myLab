package lesson05;

import lesson05.utils.JdbcUtils_DBCP;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class testdbcp {
    public static void main(String[] args) throws SQLException {
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet rs=null;
        try {
            //获取连接【唯一不一样的地方：改变的数据源 JdbcUtils_DBCP】
            connection = JdbcUtils_DBCP.getConnection();
            //sql
            String sql="SELECT * from users WHERE id>?";
            //预编译sql
            statement= connection.prepareStatement(sql);
            //设置参数
            statement.setObject(1,1);
            //执行sql
            rs=statement.executeQuery();
            //遍历结果
            while (rs.next()){
                System.out.println(rs.getObject("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils_DBCP.release(connection,statement,rs);
        }
    }
}
