package com.cbsys.saleexplore.payload;


import com.cbsys.saleexplore.validation.DobValid;
import com.cbsys.saleexplore.enums.LanguageEm;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class UserUpdatePd {

    @DobValid
    private Date dateOfBirth;

    private Integer userGender;

    private Long cityId;

    // the name cannot be long than 20 characters
    private String username; // username is not unique

    private String mobilePhoneNumber;

    private String mobilePhoneCountryCode;

    private String password;

    private LanguageEm preferLanguage;

    public UserUpdatePd(){
        // those initial values must be set to null
        dateOfBirth = null;
        userGender = null;
        cityId = null;
        username = null;
        mobilePhoneNumber = null;
        mobilePhoneCountryCode = null;
        password = null;
        preferLanguage = null;
    }

}
