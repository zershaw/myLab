package com.cbsys.saleexplore.idao;

import com.cbsys.saleexplore.entity.EmailPwdVCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IEmailPwdVeriDAO {

    EmailPwdVCode get(@Param("email") String email);

    /**
     * @return the number of rows affected, if the email already exists, the old value will be overritten
     */
    int insert(@Param("vCode") EmailPwdVCode vCode);

    /**
     * @return the number of rows affected
     */
    int delete(@Param("email") String email);

}
