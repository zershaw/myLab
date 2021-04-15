package com.cbsys.saleexplore.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OAuthRequestPd {

    @NotBlank
    private String providerId;

    @NotBlank
    private String authCode;

    private String username;

    private String imageUrl;

    private String city;

    private String gender;

    private LoginDevicePd loginDevicePayload;


}
