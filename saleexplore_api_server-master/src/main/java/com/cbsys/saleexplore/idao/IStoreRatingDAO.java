package com.cbsys.saleexplore.idao;

import com.cbsys.saleexplore.entity.StoreRating;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IStoreRatingDAO {

    List<StoreRating> get(@Param("userId") Long userId,
                          @Param("storeId") Long storeId);

    /**
     * @return the number of rows affected
     */
    int insert(@Param("rating") StoreRating rating);

    /**
     * @return the number of rows affected
     */
    int delete(@Param("userId") Long userId,
               @Param("storeId") Long storeId);


}
