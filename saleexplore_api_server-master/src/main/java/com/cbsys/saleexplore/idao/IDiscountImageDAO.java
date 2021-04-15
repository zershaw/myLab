package com.cbsys.saleexplore.idao;


import com.cbsys.saleexplore.entity.DiscountImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface IDiscountImageDAO {

    List<DiscountImage> get(@Param("id") Long id, @Param("discountId") Long discountId);

    /**
     * @return the number of rows affected
     */
    int insert(@Param("discountImage") DiscountImage discountImage);

    /**
     * @return the number of rows affected
     */
    int delete(@Param("id") Long id);

    /**
     * @return the number of rows affected
     */
    int update(@Param("id") Long id, @Param("title") String title, @Param("orderIndex") Integer orderIndex);
}
