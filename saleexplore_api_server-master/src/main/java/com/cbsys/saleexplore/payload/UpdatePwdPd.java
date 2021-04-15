package com.cbsys.saleexplore.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UpdatePwdPd {

    @NotBlank
    @Email
    String email;

    @NotBlank
    String password;

    @NotBlank
    String veriCode;

}
