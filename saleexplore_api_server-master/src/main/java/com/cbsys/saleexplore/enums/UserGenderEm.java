package com.cbsys.saleexplore.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum UserGenderEm {
    MAN(0), WOMAN(1), OTHERS(2);

    @Getter
    private final int code;

    UserGenderEm(int code) {
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
        for (UserGenderEm type : UserGenderEm.values()) {
            map.put(type.code, type);
        }
    }

    public static UserGenderEm valueOf(int type) {
        return (UserGenderEm) map.get(type);
    }
}