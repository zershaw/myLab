package com.cbsys.saleexplore.idao;

import com.cbsys.saleexplore.entity.Country;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface ICountryDAO {

    List<Country> get(@Param("id") Long id,
                      @Param("iso2") String iso2,
                      @Param("countryName") String countryName);

}
