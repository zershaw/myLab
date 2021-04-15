package com.cbsys.saleexplore.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum PopularSearchTypeEm {

    BRAND(0),
    MALL(1),
    PRODUCT(2);


    @Getter
    private final int code;

    PopularSearchTypeEm(int code) {
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
        for (PopularSearchTypeEm type : PopularSearchTypeEm.values()) {
            map.put(type.code, type);
        }
    }

    public static PopularSearchTypeEm valueOf(int type) {
        return (PopularSearchTypeEm) map.get(type);
    }

}
