package com.company.mycabinet.config;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.DefaultString;

import javax.enterprise.inject.Default;

@Source(type = SourceType.DATABASE)
public interface RolesConfig extends Config {

    @Property("optoKit.AdminRole")
    @DefaultString("0c018061-b26f-4de2-a5be-dff348347f93")
    String getAdminRole();

    @Property("optoKit.CustomerRole")
    @DefaultString("f1ed8a03-c006-0d84-82ae-2fce9b1bf99a")
    String getCustomerRole();

    @Property("optoKit.ManufacturerRole")
    @DefaultString("2377813e-d7d8-5514-8283-25559ce962fa")
    String getManufacturerRole();

}
