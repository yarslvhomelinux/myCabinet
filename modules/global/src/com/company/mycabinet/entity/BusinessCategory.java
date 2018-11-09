package com.company.mycabinet.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum BusinessCategory implements EnumClass<String> {

    MICRO("micro"),
    SMALL("small"),
    MIDDLE("middle"),
    BIGGER("bigger");

    private String id;

    BusinessCategory(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static BusinessCategory fromId(String id) {
        for (BusinessCategory at : BusinessCategory.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}