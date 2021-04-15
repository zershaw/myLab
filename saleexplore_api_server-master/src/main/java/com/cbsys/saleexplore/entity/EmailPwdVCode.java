package com.cbsys.saleexplore.entity;

import com.cbsys.saleexplore.util.DateTimeUtil;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
public class EmailPwdVCode {

    @NotBlank
    private String email;

    @NotBlank
    private String veriCode;

    private Timestamp createdTime;

    public EmailPwdVCode(String email, String veriCode){
        this.email = email;
        this.veriCode = veriCode;
        this.createdTime = new Timestamp(DateTimeUtil.getUTCTime().getTime());
    }

}
