package com.company.mycabinet.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum State implements EnumClass<String> {

    CREATED("created"),
    ADMIN_PROCESSING("adminProcessing"),
    MANUFACTURER_PROCESSING("manufacturerProcessing"),
    RESPONSE_RECEIVED("responseReceived"),
    CUSTOMER_FEEDBACK_RECEIVED("customerFeedbackReveiced"),
    RESPONSE_CLOSED("responseClosed"),
    REQUEST_CLOSED("requestClosed"),
    RESPONSE_CREATED("responseCreated");

    private String id;

    State(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static State fromId(String id) {
        for (State at : State.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}