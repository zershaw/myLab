package com.sawyer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.management.ValueExp;

/**
 * @author huanm
 * <p>
 * thymeleaf所有操作都需要通过controller实现跳转
 */
@Controller
@RequestMapping("/ems")
public class IndexController {
    /**
     * @author czx
     *访问首页
     */
    @GetMapping(value = "/index")
    public String toIndex() {
        return "ems/login";

    }
/**
 * @author czx
 * 测试thymeleaf
 */
    @GetMapping(value = "/greeting")
    public ModelAndView test(ModelAndView mv) {
        mv.setViewName("ems/greeting");
        mv.addObject("title","欢迎使用Thymeleaf!");
        return mv;
    }
    /**
     * @author czx
     * 跳转到注册页面
     */
    @GetMapping(value = "/toRegister")
    public String toRegister() {
        return "ems/regist";
    }
    /**
     * @author czx
     * 跳转到添加员工页面
     */
    @GetMapping(value = "toSave")
    public String toSave() {
        return "ems/addEmp";
    }

}
