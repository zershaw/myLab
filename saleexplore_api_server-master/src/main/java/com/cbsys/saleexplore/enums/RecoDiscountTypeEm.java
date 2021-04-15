package com.cbsys.saleexplore.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum RecoDiscountTypeEm {

    OnGoing(0), UpComing(1), Nearby(2), All(3);

    @Getter
    private final int code;

    RecoDiscountTypeEm(int code) {
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
        for (RecoDiscountTypeEm type : RecoDiscountTypeEm.values()) {
            map.put(type.code, type);
        }
    }

    public static RecoDiscountTypeEm valueOf(int type) {
        return (RecoDiscountTypeEm) map.get(type);
    }

}
