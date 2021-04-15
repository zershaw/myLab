package com.kuang.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServletDemo03 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        InputStream is =this.getServletContext().getResourceAsStream("/WEB-INF/classes/db.properties"); //以流的形式读入Resource（文件
        Properties prop = new Properties();
        prop.load(is);//建立Properties与输出流之间的关联
        String user = prop.getProperty("username");
        String pwd = prop.getProperty("password");
        resp.getWriter().print("user:"+user + " pwd:"+pwd);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
