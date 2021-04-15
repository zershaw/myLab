package com.cbsys.saleexplore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestEn extends RuntimeException {
    public BadRequestEn(String message) {
        super(message);
    }

    public BadRequestEn(String message, Throwable cause) {
        super(message, cause);
    }
}
