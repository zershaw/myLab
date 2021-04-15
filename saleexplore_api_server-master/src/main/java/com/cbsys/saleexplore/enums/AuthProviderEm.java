package com.cbsys.saleexplore.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum AuthProviderEm {
    notcare(-1),
    local(0),
    facebook(1),
    google(2),
    wechat(3);

    @Getter
    private final int code;

    AuthProviderEm(int code) {
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
        for (AuthProviderEm type : AuthProviderEm.values()) {
            map.put(type.code, type);
        }
    }

    public static AuthProviderEm valueOf(int type) {
        return (AuthProviderEm) map.get(type);
    }
}
