package com.cbsys.saleexplore.idao;

import com.cbsys.saleexplore.entity.Mall;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IMallDAO {

    List<Mall> get(@Param("id") Long id);

    List<Mall> getWithStore(@Param("cityId") Long cityId);

    /**
     * @return the number of rows affected
     */
    int insert(@Param("mall") Mall mall);

    /**
     * @return the number of rows affected
     */
    int delete(@Param("id") Long id);

}
