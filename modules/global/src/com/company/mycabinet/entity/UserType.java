package com.company.mycabinet.entity;
import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;

public enum UserType implements EnumClass<String> {

    CUSTOMER("customer"),
    MANUFACTURER("manufacturer");

    private String id;

    UserType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static UserType fromId(String id) {
        for (UserType at : UserType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
