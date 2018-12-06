package com.company.mycabinet.service;


public interface UserUtilsService {

    String NAME = "mycabinet_UserUtilsService";

    boolean isCurrentUserAdmin();

    boolean isCurrentUserCustomer();

    boolean isCurrentUserManufacturer();
}