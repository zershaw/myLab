package com.cbsys.saleexplore.idao;

import com.cbsys.saleexplore.entity.UserInterests;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IUserInterestsDAO {

    List<UserInterests> getTopKHighest(@Param("userId") long userId, @Param("topk") int topk);

    List<UserInterests> getTopKRecent(@Param("userId") long userId, @Param("topk") int topk);

    /**
     * @return the number of rows affected, if the email already exists, the old value will be overritten
     */
    void insert(@Param("userInterestsList") List<UserInterests> userInterestsList);

}
