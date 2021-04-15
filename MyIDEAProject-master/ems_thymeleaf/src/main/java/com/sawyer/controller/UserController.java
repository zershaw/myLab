package com.sawyer.controller;

import com.sawyer.entity.User;
import com.sawyer.service.UserService;
import com.sawyer.utils.ValidateImageCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * @author sawyer
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * 生成验证码
     *
     * @param session
     * @param response
     * @throws IOException
     */
    @GetMapping("/code")
    public void getVerification(HttpSession session, HttpServletResponse response) throws IOException {
        //生成验证码
        String securityCode = ValidateImageCodeUtils.getSecurityCode();
        //生成验证码图片
        BufferedImage image = ValidateImageCodeUtils.createImage(securityCode);
        //存入session中
        session.setAttribute("code", securityCode);
        //向前端响应图片
        ServletOutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }

    /**
     * 注册方法
     *
     * @param user
     * @param code
     * @param session
     * @return
     */
    @PostMapping("/register")
    public String register(User user, String code, HttpSession session) {
        String sessionCode = (String) session.getAttribute("code");
        if (sessionCode.equalsIgnoreCase(code)) { //注册成功
            userService.register(user);
            return "redirect:/ems/index";
        } else { //注册失败 回到注册页面
            return "redirect:/ems/toRegister";
        }
    }



/**
 *
 * 登录
 * @param username
 * @param password
 */
    @PostMapping(value = "/login")
    public String login(String username, String password,HttpSession session) {
        User login = userService.login(username, password);
        if (login != null) {
            session.setAttribute("user",login);
            return "redirect:/emp/findAll"; // 跳转到查询所有员工
        } else {
            return "ems/login";//跳转回到登录
        }
    }

}
