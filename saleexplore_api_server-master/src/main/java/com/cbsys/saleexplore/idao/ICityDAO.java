package com.cbsys.saleexplore.idao;

import com.cbsys.saleexplore.entity.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface ICityDAO {

    List<City> get(@Param("id") Long id,
                   @Param("cityName") String cityName,
                   @Param("countryId") Long countryId,
                   @Param("isActive") Boolean isActive);

    /**
     * @return the number of rows affected
     */
    int insert(@Param("city") City city);

    /**
     * @return the number of rows affected
     */
    int update(@Param("id") Long id,
                @Param("cityName") String cityName);

    /**
     * @return the number of rows affected
     */
    int delete(@Param("id") Long id);

}
