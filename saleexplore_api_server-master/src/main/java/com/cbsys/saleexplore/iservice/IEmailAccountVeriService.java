package com.cbsys.saleexplore.iservice;

import com.cbsys.saleexplore.entity.EmailPwdVCode;
import com.cbsys.saleexplore.entity.User;

public interface IEmailAccountVeriService {

    /**
     * send verification code through email to change password
     */
    void sendVeriCodeEmailPwd(User user);

    /**
     * send verification link through email to confirm email
     */
    void sendEmailVeryLink(User user);


    EmailPwdVCode getVCodeByEmail(String email);


    int deleteEmailVCode(String email);


}
