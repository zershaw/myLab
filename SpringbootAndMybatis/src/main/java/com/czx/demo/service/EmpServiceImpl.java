package com.czx.demo.service;

import com.czx.demo.mapper.EmployeeMapper;
import com.czx.demo.pojo.Employee;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Repository
public class EmpServiceImpl  implements EmpService{
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public void addEmployee(Employee employee) {
        employeeMapper.addEmployee(employee);

    }

    @Override
    public void deleteEmployeeByEmpId(Integer empId) {
        employeeMapper.deleteEmployeeByEmpId(empId);
    }

    @Override
    public void updateEmployee(Employee employee) {
        employeeMapper.updateEmployee(employee);
    }

    @Override
    public Page<Employee> getEmployeeList(int pageNo, int pageSize) {
        Page<Employee> page = new Page<>();
        PageHelper.startPage(pageNo,pageSize);
        List<Employee> employeeList = employeeMapper.getEmployeeList();
        PageInfo<Employee> info = new PageInfo<>(employeeList);
//        page.set(info.getList());
        page.setPageSize(info.getPages());
        page.setPageNum(info.getPageNum());
//        page.setHasNext(info.isHasNextPage());
//        page.setHasPre(info.isHasPreviousPage());

        return page;
    }

    @Override
    public Employee getEmployeeByEmpId(Integer empId) {
        return employeeMapper.getEmployeeByEmpId(empId);
    }

}
