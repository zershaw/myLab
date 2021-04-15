package com.cbsys.saleexplore.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Login attributes
 */
@Data
public class LoginRequestPd {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    private LoginDevicePd loginDevicePayload;

}
