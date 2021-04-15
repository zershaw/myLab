package com.cbsys.saleexplore.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum DiscountRatingEnum {

    DISLIKE(0),
    LIKE(1),
    CANCLE(2);

    @Getter
    private final int code;

    DiscountRatingEnum(int code) {
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
        for (DiscountRatingEnum type : DiscountRatingEnum.values()) {
            map.put(type.code, type);
        }
    }

    public static DiscountRatingEnum valueOf(int type) {
        return (DiscountRatingEnum) map.get(type);
    }
}
