package com.cbsys.saleexplore.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum AdsRedirectTypeEm {

    Discount(0), Store(1), WebLink(2);

    @Getter
    private final int code;

    AdsRedirectTypeEm(int code) {
        this.code = code;
    }

    @JsonValue
    public int toValue() {
        return ordinal();
    }

    /*
     * support from int to corresponding type
     */
    private static Map map = new HashMap<>();

    static {
        for (AdsRedirectTypeEm type : AdsRedirectTypeEm.values()) {
            map.put(type.code, type);
        }
    }

    public static AdsRedirectTypeEm valueOf(int type) {
        return (AdsRedirectTypeEm) map.get(type);
    }



}
