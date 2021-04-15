package com.cbsys.saleexplore.iservice;

import com.cbsys.saleexplore.payload.OAuthRequestPd;

public interface IAuthService {


    /**
     * Validate the facebook authentication code
     * @return null if validation failed
     */
    boolean validateFacebookToken(OAuthRequestPd oauthRequest);

    /**
     * based on the access_token, get the facebook user com.cbsys.saleexplore.profile
     */
    void loadFacebookProfile(OAuthRequestPd oauthRequest);

    /**
     * Validate the google authentication code
     * @return null if validation failed
     */
    boolean validateGoogleToken(OAuthRequestPd oauthRequest);

    /**
     * based on the access_token, get the google user com.cbsys.saleexplore.profile
     */
    void loadGoogleProfile(OAuthRequestPd oauthRequest);

    /**
     * Validate the wechat authentication code
     * @return null if validation failed
     */
    boolean validateWechatToken(OAuthRequestPd oauthRequest);

    /**
     * based on the access_token, get the wechat user com.cbsys.saleexplore.profile
     */
    void loadWechatProfile(OAuthRequestPd oauthRequest);


}
