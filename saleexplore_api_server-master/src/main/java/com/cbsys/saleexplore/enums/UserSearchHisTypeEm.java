package com.cbsys.saleexplore.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum UserSearchHisTypeEm {

    DISCOUNT(0),
    STORE(1);

    @Getter
    private final int code;

    UserSearchHisTypeEm(int code) {
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
        for (UserSearchHisTypeEm type : UserSearchHisTypeEm.values()) {
            map.put(type.code, type);
        }
    }

    public static UserSearchHisTypeEm valueOf(int type) {
        return (UserSearchHisTypeEm) map.get(type);
    }

}
