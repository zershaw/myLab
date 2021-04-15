package com.cbsys.saleexplore.idao;

import com.cbsys.saleexplore.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface ICategoryDAO {

    List<Category> getAll();

}
