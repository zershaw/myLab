package com.cbsys.saleexplore.idao;

import com.cbsys.saleexplore.entity.UserSearchHistory;
import com.cbsys.saleexplore.enums.UserSearchHisTypeEm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IUserSearchHistoryDAO {

    /**
     * @return the number of rows affected
     */
    int insert(@Param("searchHistory") UserSearchHistory searchHistory);

    List<String> get(@Param("userId") long userId,
                     @Param("searchType") UserSearchHisTypeEm searchType,
                     @Param("topk") int topk);

    /**
     * delete user's searching history
     */
    void delete(@Param("userId") long userId,
                @Param("searchType") UserSearchHisTypeEm searchType);

    /**
     * delete user's searching history
     */
    void deleteByKw(@Param("userId") long userId,
                    @Param("searchType") UserSearchHisTypeEm searchType,
                    @Param("kwQuery") String kwQuery);
}
