package com.cbsys.saleexplore.idao;

import com.cbsys.saleexplore.entity.UserLoginDevice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IUserLoginDeviceDAO {

    /**
     * Get a single user
     */
    UserLoginDevice get(@Param("id") Long id);


    /**
     * insert extra user login device infor to the db
     * the user must be already inserted in the T_USER table
     * @return the number of rows affected
     */
    int insert(@Param("userLoginDevice") UserLoginDevice userLoginDevice, @Param("id") Long id);


}
