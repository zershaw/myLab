package com.kuang.servlet;

import com.kuang.pojo.Person;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class SessionDemo01 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        //解决乱码问题
                resp.setContentType("text/html;charset=utf-8");
                req.setCharacterEncoding("UTF-8");
                resp.setCharacterEncoding("UTF-8");
                //得到session问题
        HttpSession session = req.getSession();

        //给session中存东西 setAttribute：使用指定的名称将对象绑定到此session。 如果同名对象已绑定到会话，则替换该对象。
        session.setAttribute("SessionName",new Person("陈泽潇",10));

        //获取session的ID
        String sessionId = session.getId();

        //判断Session是不是新创建
        if (session.isNew()){
            resp.getWriter().print("session创建完成，sessionID为 :"+ sessionId );
        }else {
            resp.getWriter().print("session已经在服务器中存在，sessionID为 :"+ sessionId );
        }

        //Session创建的时候做了什么事情
        //服务器把sessionID以cookie的形式发送给客户端，相当于这段代码
//        Cookie cookie = new Cookie("JSESSIONID",sessionId);
//        cookie.setMaxAge(-1);
//        resp.addCookie(cookie);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
