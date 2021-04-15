package com.example.springboot06security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouterController {
    @RequestMapping({"/","/index"})
    public String index(){
        return "index";
    }
    @RequestMapping({"/tologin"})
    public String tologin(){
        return "login";
    }
    @RequestMapping({"/level1/{id}"})
    public String level1(@PathVariable("id") int id){
        return "temp_views/level-1/"+id;
    }
    @RequestMapping({"/level2/{id}"})
    public String level2(@PathVariable("id") int id){
        return "temp_views/level-2/"+id;
    }
    @RequestMapping({"/level3/{id}"})
    public String level3(@PathVariable("id") int id){
        return "temp_views/level-3/"+id;
    }
}
