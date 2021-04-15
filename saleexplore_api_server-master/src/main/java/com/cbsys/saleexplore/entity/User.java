package com.cbsys.saleexplore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cbsys.saleexplore.enums.LanguageEm;
import lombok.Data;
import com.cbsys.saleexplore.enums.AuthProviderEm;
import com.cbsys.saleexplore.enums.OsTypeEm;
import com.cbsys.saleexplore.enums.UserGenderEm;

import java.sql.Timestamp;
import java.util.Date;


@Data
public class User {

    /*
     * basic infors
     */
    private Long id; // primary key

    private String email; // the email is unique

    private String imageName;

    @JsonIgnore
    private String password;

    private String username; // username is not unique

    private UserGenderEm userGender;

    private Date dateOfBirth;

    private String mobilePhoneCountryCode;

    private int mobilePhoneNumber;

    private int cityId;

    private LanguageEm preferLanguage;


    /*
     * sign tracks
     */
    private Timestamp lastTimeLogin;

    private Timestamp lastTimeActive;

    private Timestamp dateTimeSignUp;

    private OsTypeEm osType;  // 0 represents IOS, 1 represents Android

    // which version the user is using
    private int appVersionCode;

    // user device token and unique id
    private String deviceToken;

    private AuthProviderEm authProvider;

    private String providerId;

    /*
     * last login time geo-location
     */
    private float latitude;

    private float longitude;


    /*
     * flags
     */
    private boolean isDeleted = false;

    private boolean isEnabled = true;

    private boolean isHunterEnabled = true;

    private boolean isHunterPremium = false;

    private boolean isEmailVerified = false;

    private boolean isEmailMarketEnabled = true;


    /*
     * non persistent info of city
     */
    private City city;

}