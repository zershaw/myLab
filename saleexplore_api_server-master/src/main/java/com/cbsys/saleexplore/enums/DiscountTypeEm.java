package com.cbsys.saleexplore.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum DiscountTypeEm {

    SingleItem(0),
    GroupItem(1),
    AllItem(2);

    @Getter
    private final int code;

    DiscountTypeEm(int code) {
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
        for (DiscountTypeEm type : DiscountTypeEm.values()) {
            map.put(type.code, type);
        }
    }

    public static DiscountTypeEm valueOf(int type) {
        return (DiscountTypeEm) map.get(type);
    }

}
