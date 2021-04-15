package com.cbsys.saleexplore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class SensitiveWordsEn extends RuntimeException {

    private List<String> illegalText;

    public SensitiveWordsEn(List<String> illegalText) {
        this.illegalText = illegalText;
    }
}
