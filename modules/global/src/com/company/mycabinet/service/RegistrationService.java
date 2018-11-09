package com.company.mycabinet.service;


import java.util.Map;

public interface RegistrationService {
    String NAME = "mycabinet_RegistrationService";

    void registerStandartUser(Map<String, Object> paramsMap);

    boolean isUniqueUserLogin(String login);
}