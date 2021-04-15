package com.kuang.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //写一张图片
        BufferedImage bufferedImage = new BufferedImage(80,20,BufferedImage.TYPE_INT_RGB);//需要一张画板
        Graphics graphics = bufferedImage.getGraphics();//需要一只画笔
        //设置背景
        graphics.setColor(Color.white);//设置颜色
        graphics.fillRect(0,0,80,20);
        //设置内容
        graphics.setColor(Color.blue);
        graphics.setFont(new Font(null,Font.ITALIC,20));
        graphics.drawString(Objects.requireNonNull(makeNum()),0,20);

        //告诉浏览器，这个请求用图片的方式打开
        resp.setContentType("image/jpeg");
        //网站存在缓存，不让浏览器缓存
        resp.setDateHeader("expires",-1);
        resp.setHeader("Cache-Control","no-cache");
        resp.setHeader("Pragma","no-cache");
        //给这个响应设置三秒刷新一次，且把缓存机制关掉
        resp.setHeader("refresh","3");
        //将这张图片写入浏览器
        ImageIO.write(bufferedImage,"jpg",resp.getOutputStream());
    }
    //生成随机数
    private String makeNum() {
        //懒得写
        return "懒得写";
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
