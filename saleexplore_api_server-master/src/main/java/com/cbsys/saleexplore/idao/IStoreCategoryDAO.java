package com.cbsys.saleexplore.idao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface IStoreCategoryDAO {
    List<Integer> getStoreCategoryIds(@Param("storeId") long storeId);
}
