package com.czx.spring03web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
//建议在/config/myMvcConfig里统一配置
    @RequestMapping("/test") //访问路径
    public String test(Model model) {
        model.addAttribute("msg","hello thymeleaf");
        return "test-2";//作为被访问的html的文件名
    }
//    @RequestMapping({"/","/index.html"})
//    public String index(){
//        return "index";
//    }
}
