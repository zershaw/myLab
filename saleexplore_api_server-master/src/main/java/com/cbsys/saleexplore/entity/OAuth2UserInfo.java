package com.cbsys.saleexplore.entity;

import lombok.Data;

/**
 * the information we extracted from the oauth2 providers for each user
 */

@Data
public class OAuth2UserInfo {

    public String username;

    // user com.cbsys.saleexplore.profile image url
    public String imageUrl;

    // the user's registeration id in oauth2 provider's system
    public String providerId;

}
