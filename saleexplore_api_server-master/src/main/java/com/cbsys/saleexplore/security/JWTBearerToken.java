package com.cbsys.saleexplore.security;

import lombok.Data;

@Data
public class JWTBearerToken {

    private String accessToken;
    private String tokenType = "Bearer";


    public JWTBearerToken(String accessToken) {
        this.accessToken = accessToken;
    }


}
