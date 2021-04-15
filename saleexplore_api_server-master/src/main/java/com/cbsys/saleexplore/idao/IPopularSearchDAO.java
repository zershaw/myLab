package com.cbsys.saleexplore.idao;

import com.cbsys.saleexplore.enums.PopularSearchTypeEm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IPopularSearchDAO {
    List<String> getPopularSearch(@Param("searchType") PopularSearchTypeEm searchType,
                                  @Param("topk") int topk);
}
