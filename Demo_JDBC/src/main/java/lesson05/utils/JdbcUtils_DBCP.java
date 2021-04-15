package lesson05.utils;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcUtils_DBCP {

    private static DataSource source=null;
    static {
        try {

            InputStream in = JdbcUtils_DBCP.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
            Properties properties = new Properties();
            properties.load(in);

            //创建 数据源 工厂模式--> 创建对象
            source = BasicDataSourceFactory.createDataSource(properties);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    // 获取连接
    public static Connection getConnection() throws SQLException {
        //从数据源中获取连接
        return source.getConnection();
    }
    //释放连接资源
    public static void  release(Connection conn, Statement st, ResultSet rs) throws SQLException {
        if(conn!=null) conn.close();

        if(st!=null) st.close();

        if(rs!=null) rs.close();
    }
}
