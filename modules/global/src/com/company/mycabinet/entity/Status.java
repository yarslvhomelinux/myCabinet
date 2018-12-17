package com.company.mycabinet.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum Status implements EnumClass<String> {

    REQUEST_CREATED("requestCreated"),
    REQUEST_ADMIN_PROCESSING("requestAdminProcessing"),
    MANUFACTURER_PROCESSING("manufacturerProcessing"),
    REQUEST_CLOSED("requestClosed"),
    RESPONSE_CREATED("responseCreated"),
    RESPONSE_ADMIN_PROCESSING("responseAdminProcessing"),
    RESPONSE_RECEIVED("responseReceived"),
    RESPONSE_SPECIFY("responseSpecify"),
    CUSTOMER_FEEDBACK_RECEIVED("customerFeedbackReveiced"),
    RESPONSE_SPECIFY_ADM_PROCESSING("responseSpecifyAdminProcessing"),
    RESPONSE_AGREE("responseAgree"),
    RESPONSE_DISAGREE("responseDisagree"),
    RESPONSE_CLOSED("responseClosed"),
    RESPONSE_SPECIFY_GOT("responseSpecifyGot");

    private String id;

    Status(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Status fromId(String id) {
        for (Status at : Status.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}