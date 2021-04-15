package com.cbsys.saleexplore.idao;

import com.cbsys.saleexplore.entity.DiscountRating;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IDiscountRatingDAO {

    List<DiscountRating> get(@Param("userId") Long userId,
                             @Param("discountId") Long discountId);

    /**
     * @return the number of rows affected
     */
    int insert(@Param("rating") DiscountRating rating);

    /**
     * @return the number of rows affected
     */
    int delete(@Param("userId") Long userId,
               @Param("discountId") Long discountId);

}
