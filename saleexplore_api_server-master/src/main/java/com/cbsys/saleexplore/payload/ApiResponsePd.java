package com.cbsys.saleexplore.payload;
/**
 * API Response payload
 */

import lombok.Data;

@Data
public class ApiResponsePd {

    private boolean success;
    private Object message;


    public ApiResponsePd(boolean success, Object message) {
        this.success = success;
        this.message = message;
    }

}
