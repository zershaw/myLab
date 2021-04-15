package com.kuang.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

public class Fileservlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//      ○ 获取下载文件的路径
//      String realPath = this.getServletContext().getRealPath("/download.png");
        String realPath = "E:\\Javaweb-01-servlet\\response\\target\\classes\\download.jpg";
//		○ 下载文件的文件名是什么？
        String fileName = realPath.substring(realPath.lastIndexOf("\\")+1);
//		○ 设置想办法让浏览器能够支持(Content-disposition)下载我们需要的东西 逗号前后是键值对
        resp.setHeader("Content-disposition","attachment;filename="+fileName);//响应头:
        //Content-disposition 是 MIME 协议的扩展，MIME 协议指示 MIME 用户代理如何显示附加的文件。
        // Content-disposition其实可以控制用户请求所得的内容存为一个文件的时候提供一个默认的文件名，文件直接在浏览器上显示或者在访问时弹出文件下载对话框。
//		○ 获取下载文件的输入流
        FileInputStream in = new FileInputStream(realPath);
//		○ 创建缓冲区
        int len =0;
        byte[] buffer = new byte[2014];
//		○ 获取OutputStream对象
        ServletOutputStream out = resp.getOutputStream();
//		○ 将FileOutputStream【流】写入buffer缓冲区，使用OutputStream将缓冲区中的数据输出到客户端
        while((len=in.read(buffer))>0){
            out.write(buffer,0,len);
        }
		in.close();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
