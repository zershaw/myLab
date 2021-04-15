package com.cbsys.saleexplore.idao;

import com.cbsys.saleexplore.entity.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface IBrandDAO {

    List<Brand> get(@Param("ids") List<Integer> ids);
}
