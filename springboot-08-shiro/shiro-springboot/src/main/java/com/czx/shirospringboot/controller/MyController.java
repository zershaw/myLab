package com.czx.shirospringboot.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {
    @RequestMapping({"/index"})
    public String toIndex(Model model){
        model.addAttribute("msg","hello");
        return "index";
    }

    @RequestMapping("/user/add")
    public String add(){
        return "user/add";
    }

    @RequestMapping("/user/update")
    public String update(){
        return "user/update";
    }

    @RequestMapping({"/","/toLogin"})
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(String username, String password,Model model){
        // 通过 SecurityUtils 获取当前执行的用户 Subject
        Subject currentUser = SecurityUtils.getSubject();
        //封装用户的登录数据生成一个[令牌]
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);

        //执行登录方法，如果没有异常就说明ok了
        try {
            // 执行登录操作
            currentUser.login(token);
            //成功则进入首页
            System.out.println("登陆成功");
            return "index";
        } catch (UnknownAccountException uae) {
            // 如果用户名不存在
            System.out.println("用户名不存在");
            model.addAttribute("exception","用户名错误");
            return "login";
        } catch (IncorrectCredentialsException ice) {
            // 如果密码不正确
            System.out.println("密码错误");
            model.addAttribute("exception","密码错误");
            return "login";
        }
    }
//退出登录

    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/toLogin";
    }

    @RequestMapping("/noAuth")
    @ResponseBody
    public String unAuthorized(){
        return  "未经授权无法访问此页面";
    }
}
