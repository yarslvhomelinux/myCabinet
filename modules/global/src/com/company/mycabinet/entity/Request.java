package com.company.mycabinet.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Lob;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.List;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import com.haulmont.cuba.core.entity.FileDescriptor;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamePattern("%s %s|productCategory,productType")
@Table(name = "MYCABINET_REQUEST")
@Entity(name = "mycabinet$Request")
public class Request extends StandardEntity {
    private static final long serialVersionUID = -8386218815895673715L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_CATEGORY_ID")
    protected ProductCategory productCategory;

    @Temporal(TemporalType.DATE)
    @Column(name = "ASSIGN_DATE")
    protected Date assignDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATOR_ID")
    protected ExtUser creator;

    @Column(name = "REQUEST_NUMBER")
    protected String requestNumber;

    @Lob
    @Column(name = "PRODUCT_TYPE")
    protected String productType;

    @Column(name = "BRAND", length = 1000)
    protected String brand;

    @Lob
    @Column(name = "PRODUCT_DESCRIPTION")
    protected String productDescription;

    @Lob
    @Column(name = "PRODUCT_VOLUME")
    protected String productVolume;

    @Column(name = "DELIVERY_TIME")
    protected Integer deliveryTime;

    @Lob
    @Column(name = "DELIVERY_REGION")
    protected String deliveryRegion;

    @Column(name = "CONTACT_PERSON", length = 500)
    protected String contactPerson;

    @Column(name = "CONTACT_PERSON_PHONE", length = 500)
    protected String contactPersonPhone;

    @Column(name = "STATUS")
    protected String status;

    @JoinTable(name = "MYCABINET_REQUEST_EXT_USER_LINK",
        joinColumns = @JoinColumn(name = "REQUEST_ID"),
        inverseJoinColumns = @JoinColumn(name = "EXT_USER_ID"))
    @ManyToMany
    protected List<ExtUser> manufacturer;

    @OneToMany(mappedBy = "request")
    protected List<Response> response;

    @OneToMany(mappedBy = "request")
    protected List<Attachment> attachment;

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }

    public Date getAssignDate() {
        return assignDate;
    }


    public void setCreator(ExtUser creator) {
        this.creator = creator;
    }

    public ExtUser getCreator() {
        return creator;
    }


    public List<ExtUser> getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(List<ExtUser> manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<Attachment> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<Attachment> attachment) {
        this.attachment = attachment;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

    public List<Response> getResponse() {
        return response;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setStatus(Status status) {
        this.status = status == null ? null : status.getId();
    }

    public Status getStatus() {
        return status == null ? null : Status.fromId(status);
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductType() {
        return productType;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductVolume(String productVolume) {
        this.productVolume = productVolume;
    }

    public String getProductVolume() {
        return productVolume;
    }

    public void setDeliveryTime(Integer deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Integer getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryRegion(String deliveryRegion) {
        this.deliveryRegion = deliveryRegion;
    }

    public String getDeliveryRegion() {
        return deliveryRegion;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPersonPhone(String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }

    public String getContactPersonPhone() {
        return contactPersonPhone;
    }
}