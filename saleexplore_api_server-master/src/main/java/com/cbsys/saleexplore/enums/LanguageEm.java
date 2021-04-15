package com.cbsys.saleexplore.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum LanguageEm {

    ENGLISH(0), CHINESE(1);

    @Getter
    private final int code;

    LanguageEm(int code) {
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
        for (LanguageEm type : LanguageEm.values()) {
            map.put(type.code, type);
        }
    }

    public static LanguageEm valueOf(int type) {
        return (LanguageEm) map.get(type);
    }


}
