package com.czx.spring03web.controller;

import com.czx.spring03web.dao.DepartmentDao;
import com.czx.spring03web.dao.EmployeeDao;
import com.czx.spring03web.pojo.Department;
import com.czx.spring03web.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    DepartmentDao departmentDao;

    @RequestMapping("/emps")
    public String list (Model model){
        Collection<Employee> employees = employeeDao.getAllEmployees();
        model.addAttribute("emps",employees);//把查询结果返回给model
        return "emp/list"; // 最后，令浏览器跳转到这个页面！！！！！！！！！！！！
    }

    //【加载】页面的部门信息
    @GetMapping("/addoneemp")
    public String toAddpage(Model model){
        //查出所有部门信息
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departments", departments);
        return "emp/add";
    }

    //接受传来的Employee对象并像底层业务【添加】
    @PostMapping("/addoneemp")
    public String add(Employee employee) {
        System.out.println(employee);
        //调用底层业务方法保存员工信息
        employeeDao.addEmployee(employee);
        return "redirect:/emps";
    }

    //【前往】员工的修改页面
    @GetMapping("/emps/{id}")
    public String toUpdateEmp(@PathVariable("id")Integer id,Model model){ //从url得到的id
        Employee employee = employeeDao.getEmployeeByID(id);
        model.addAttribute("emp",employee); //前端得到了叫“emp”的model
        //查出所有部门信息
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departments", departments);
        return "emp/update";
    }

    //【修改】信息
    @PostMapping("/updateEmp")
    public String update(Employee employee){
        employeeDao.addEmployee(employee);
    return "redirect:/emps";
    }

    //删除员工
    @GetMapping("/delemp/{id}")
    public String deleteEmp(@PathVariable("id")Integer id){ //从url得到的id
        employeeDao.deleteEmployeeByID(id);
        return "forward:/emps"; //redirect和forward都行，不加的话就是直接访问静态资源
    }
}
