package com.czx.demo.mapper;

import com.czx.demo.pojo.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DepartmentMapper {

    /**
     * 获取部门列表
     * @return
     */
    List<Department> getDepartmentList();

    /**
     * 根据部门ID查询部门信息
     * @param deptId
     * @return
     */
    Department getDepartmentByDeptId(Integer deptId);

    /**
     * 根据部门ID查询部门详情
     * @param deptId
     * @return
     */
    Department getDepartmentByDeptId_(Integer deptId);
}
