package com.cbsys.saleexplore.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum StoreOrderByEm {

    DEFAULT(0),
    DISTANCE(1),
    POPULARITY(2),
    ENVIRONMENT(3),
    SERVICE_QUALITY(4);

    @Getter
    private final int code;

    StoreOrderByEm(int code) {
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
        for (StoreOrderByEm type : StoreOrderByEm.values()) {
            map.put(type.code, type);
        }
    }

    public static StoreOrderByEm valueOf(int type) {
        return (StoreOrderByEm) map.get(type);
    }

}
