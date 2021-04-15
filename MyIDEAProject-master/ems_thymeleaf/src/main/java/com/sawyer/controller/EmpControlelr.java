package com.sawyer.controller;

import com.sawyer.entity.Emp;
import com.sawyer.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.PastOrPresent;
import java.util.List;

@Controller
@RequestMapping(value = "/emp")
public class EmpControlelr {

    @Autowired
    private EmpService empService;

    //find和findAll不是重定向，直接到跳转页面，并且直接跳转classpath下template是自带“/”的
    //重定向的话不是从template下，是需要加/的
    //查找不是重定向，不加/；重定向需要加/
    @GetMapping(value = "/findAll")
    public String findAll(Model model) {
        List<Emp> allList = empService.findAll();
        model.addAttribute("emps", allList);
        return "ems/emplist";
    }

/**
*添加用户
 * @param emp
*/
    @PostMapping(value = "/save")
    public String save(Emp emp) {
        empService.save(emp);
        return "redirect:/emp/findAll";
    }

    /**
     *删除用户
     * @param id
     */
    @GetMapping(value = "/delete")
    public String delete(String id) {
        empService.delete(id);
        return "redirect:/emp/findAll";
    }

    /**
     *根据id获取用户信息
     * @param id
     * @param model
     */
    @GetMapping(value = "/find")
    public String find(String id, Model model) {
        Emp emp = empService.find(id);
        model.addAttribute("emp", emp);
        return "ems/updateEmp";
    }

    /**
     *更新用户
     * @param emp
     */
    @PostMapping(value = "update")
    public String update(Emp emp) {
        empService.update(emp);
        return "redirect:/emp/findAll";
    }
}
