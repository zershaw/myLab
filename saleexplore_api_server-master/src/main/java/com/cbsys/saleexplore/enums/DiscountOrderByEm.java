package com.cbsys.saleexplore.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum DiscountOrderByEm {

    DEFAULT(0),
    DISTANCE(1),
    RATING(2),
    LOW_PRICE_FIRST(3),
    HIGH_PRICE_FIRST(4);

    @Getter
    private final int code;

    DiscountOrderByEm(int code) {
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
        for (DiscountOrderByEm type : DiscountOrderByEm.values()) {
            map.put(type.code, type);
        }
    }

    public static DiscountOrderByEm valueOf(int type) {
        return (DiscountOrderByEm) map.get(type);
    }

}
