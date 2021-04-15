package com.cbsys.saleexplore.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum AdsDisplayTypeEm {

    BANNER(0), SPLASH(1);

    @Getter
    private final int code;

    AdsDisplayTypeEm(int code) {
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
        for (AdsDisplayTypeEm type : AdsDisplayTypeEm.values()) {
            map.put(type.code, type);
        }
    }

    public static AdsDisplayTypeEm valueOf(int type) {
        return (AdsDisplayTypeEm) map.get(type);
    }



}
