package com.sawyer.dao;

import com.sawyer.entity.Emp;

import java.util.List;

public interface EmpDAO {
    List<Emp> findAll(); //查询所有

    void save(Emp emp);

    void delete(String id);

    Emp find(String id);

    void update(Emp emp);
}
