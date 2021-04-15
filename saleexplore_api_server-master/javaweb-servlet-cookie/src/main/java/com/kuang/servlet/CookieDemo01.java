package com.kuang.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

//保存用户上一次访问的时间
public class CookieDemo01 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        //解决中文乱码
        resp.setContentType("text/html");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();

        //Cookie：服务器从客户端获取，用数组表示可能有多个
        Cookie[] cookies =req.getCookies();

        if(cookies !=null){
            out.write("你上次访问的时间是：\n");
            for (int i = 0; i<cookies.length;i++){
                Cookie cookie = cookies[i];
                if (cookie.getName().equals("lastLoginTime")){ //getName拿到的是键值对的名字！即，键
                    //获取cookie中的值
                    long lastLoginTime =Long.parseLong(cookie.getValue());
                    Date data = new Date(lastLoginTime);
                    out.write(data.toLocaleString());
                }
            }
        }
        else {
            out.write("这是您第一次访问本站");
        }
        //服务给客户端响应一个cookie
        //Cookie cookie = new Cookie("name","czx");
        Cookie cookie = new Cookie("lastLoginTime",System.currentTimeMillis()+"");
        //设置cookie的时间
        cookie.setMaxAge(24*60*60);
        resp.addCookie(cookie);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
