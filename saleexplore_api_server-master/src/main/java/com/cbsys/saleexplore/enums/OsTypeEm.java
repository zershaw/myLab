package com.cbsys.saleexplore.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum OsTypeEm {

    IOS(0), ANDROID(1), OTHER(2);

    @Getter
    private final int code;

    OsTypeEm(int code) {
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
        for (OsTypeEm type : OsTypeEm.values()) {
            map.put(type.code, type);
        }
    }

    public static OsTypeEm valueOf(int type) {
        return (OsTypeEm) map.get(type);
    }

}
