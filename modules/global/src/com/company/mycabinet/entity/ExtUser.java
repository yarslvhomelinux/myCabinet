package com.company.mycabinet.entity;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.haulmont.cuba.core.entity.annotation.Extends;
import com.haulmont.cuba.security.entity.User;

import java.util.Date;

@Entity(name = "mycabinet$ExtUser")
@Extends(User.class)
public class ExtUser extends User {

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTHDAY")
    protected Date birthday;

    @Column(name = "PHONE_NUMBER", length = 500)
    protected String phoneNumber;

    @Column(name = "ORGANIZATION_NAME", length = 600)
    protected String organizationName;

    @Column(name = "ACTIVITY_TYPE", length = 1000)
    protected String activityType;

    @Column(name = "LEGAL_ADDRESS", length = 600)
    protected String legalAddress;

    @Column(name = "ACTUAL_ADDRESS", length = 600)
    protected String actualAddress;

    @Column(name = "BUSINESS_CATEGORY")
    protected String businessCategory;

    @Column(name = "MANUFACTURER_LEGAL_ADRESS")
    protected String manufacturerLegalAdress;

    @Column(name = "FIRM_AGE")
    protected Integer firmAge;

    @Column(name = "PRODUCTION_VOLUME", length = 600)
    protected String productionVolume;

    @Column(name = "USER_TYPE")
    protected String userType;

    @Column(name = "GOODS_CATEGORY", length = 600)
    protected String goodsCategory;

    public BusinessCategory getBusinessCategory() {
        return businessCategory == null ? null : BusinessCategory.fromId(businessCategory);
    }

    public void setBusinessCategory(BusinessCategory businessCategory) {
        this.businessCategory = businessCategory == null ? null : businessCategory.getId();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserType getUserType() {
        return userType == null ? null : UserType.fromId(userType);
    }

    public void setUserType(UserType userType) {
        this.userType = userType == null ? null : userType.getId();
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setLegalAddress(String legalAddress) {
        this.legalAddress = legalAddress;
    }

    public String getLegalAddress() {
        return legalAddress;
    }

    public void setActualAddress(String actualAddress) {
        this.actualAddress = actualAddress;
    }

    public String getActualAddress() {
        return actualAddress;
    }

    public void setManufacturerLegalAdress(String manufacturerLegalAdress) {
        this.manufacturerLegalAdress = manufacturerLegalAdress;
    }

    public String getManufacturerLegalAdress() {
        return manufacturerLegalAdress;
    }

    public void setFirmAge(Integer firmAge) {
        this.firmAge = firmAge;
    }

    public Integer getFirmAge() {
        return firmAge;
    }

    public void setProductionVolume(String productionVolume) {
        this.productionVolume = productionVolume;
    }

    public String getProductionVolume() {
        return productionVolume;
    }

    public void setGoodsCategory(String goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    public String getGoodsCategory() {
        return goodsCategory;
    }
}