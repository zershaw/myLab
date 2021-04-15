package com.cbsys.saleexplore.idao;


import com.cbsys.saleexplore.entity.StoreImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IStoreImageDAO {

    List<StoreImage> get(@Param("id") Long id, @Param("storeId") Long storeId);

    /**
     * @return the number of rows affected
     */
    int insert(@Param("storeImage") StoreImage storeImage);

    /**
     * @return the number of rows affected
     */
    int delete(@Param("id") Long id);

    /**
     * @return the number of rows affected
     */
    int update(@Param("id") Long id, @Param("title") String title, @Param("orderIndex") Integer orderIndex);
}
