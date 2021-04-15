package com.czx.demo.service;

import com.czx.demo.pojo.Employee;
import com.github.pagehelper.Page;

public interface EmpService {
    /**
     * 添加员工
     * @param employee
     */
    void addEmployee(Employee employee);


    /**
     * 删除员工
     */
    void deleteEmployeeByEmpId(Integer empId);


    /**
     * 更新员工
     */
    void updateEmployee(Employee employee);

    /**
     * 查询员工列表
     * @return
     */
    Page<Employee> getEmployeeList(int pageNo, int pageSize);

    /**
     * 根据员工ID查询员工信息
     * @param empId
     * @return
     */
    Employee getEmployeeByEmpId(Integer empId);
}
