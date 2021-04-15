package com.cbsys.saleexplore.idao;

import com.cbsys.saleexplore.entity.Ads;
import com.cbsys.saleexplore.enums.AdsDisplayTypeEm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IAdsDAO {

    List<Ads> get(@Param("id") Long id, @Param("discountId") Long discountId,
                  @Param("storeId") Long storeId,
                  @Param("displayType") AdsDisplayTypeEm displayType,
                  @Param("limit") Integer limit);

    /**
     * @return the number of rows affected
     */
    int insert(@Param("adsBanner") Ads adsBanner);

    /**
     * @return the number of rows affected
     */
    int update(@Param("id") Long id,
               @Param("imageName") String imageName,
               @Param("isActive") Integer isActive);


}
