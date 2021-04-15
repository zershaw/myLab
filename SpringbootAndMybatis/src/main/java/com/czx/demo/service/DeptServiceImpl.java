package com.czx.demo.service;

import com.czx.demo.mapper.DepartmentMapper;
import com.czx.demo.pojo.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Repository
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Override
    public List<Department> getDepartmentList() {
        return departmentMapper.getDepartmentList();
    }
}