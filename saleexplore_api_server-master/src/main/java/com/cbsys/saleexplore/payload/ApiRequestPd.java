package com.cbsys.saleexplore.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ApiRequestPd {
    @NotBlank
    private String authToken;
}
