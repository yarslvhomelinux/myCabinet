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
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.FileDescriptor;
import java.util.List;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import javax.persistence.OneToMany;

@NamePattern("%s %s|isPriceSatisfied,deliveryPrice")
@Table(name = "MYCABINET_RESPONSE")
@Entity(name = "mycabinet$Response")
public class Response extends StandardEntity {
    private static final long serialVersionUID = -7537095104186107113L;

    @Column(name = "PRICE")
    protected Integer price;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATOR_ID")
    protected ExtUser creator;

    @Column(name = "DELIVERY_PRICE")
    protected Integer deliveryPrice;

    @Lob
    @Column(name = "MANUFACTURER_COMMENT")
    protected String manufacturerComment;

    @Lob
    @Column(name = "IS_PRICE_SATISFIED")
    protected String isPriceSatisfied;

    @Lob
    @Column(name = "CONTACT")
    protected String contact;

    @Lob
    @Column(name = "CUSTOMER_COMMENT")
    protected String customerComment;

    @Lob
    @Column(name = "CLOSE_COMMENT")
    protected String closeComment;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REQUEST_ID")
    protected Request request;

    @JoinTable(name = "MYCABINET_RESPONSE_FILE_DESCRIPTOR_LINK",
        joinColumns = @JoinColumn(name = "RESPONSE_ID"),
        inverseJoinColumns = @JoinColumn(name = "FILE_DESCRIPTOR_ID"))
    @ManyToMany
    @OnDelete(DeletePolicy.CASCADE)
    protected List<FileDescriptor> attachment;

    @Column(name = "STATE")
    protected String state;

    @Lob
    @Column(name = "MANUFACTURER_INFO")
    protected String manufacturerInfo;

    @OneToMany(mappedBy = "response")
    protected List<Attachment> attach;

    public void setAttach(List<Attachment> attach) {
        this.attach = attach;
    }

    public List<Attachment> getAttach() {
        return attach;
    }


    public List<FileDescriptor> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<FileDescriptor> attachment) {
        this.attachment = attachment;
    }



    public void setManufacturerInfo(String manufacturerInfo) {
        this.manufacturerInfo = manufacturerInfo;
    }

    public String getManufacturerInfo() {
        return manufacturerInfo;
    }


    public void setCreator(ExtUser creator) {
        this.creator = creator;
    }

    public ExtUser getCreator() {
        return creator;
    }


    public Status getState() {
        return state == null ? null : Status.fromId(state);
    }

    public void setState(Status state) {
        this.state = state == null ? null : state.getId();
    }




    public void setRequest(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }


    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setDeliveryPrice(Integer deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public Integer getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setManufacturerComment(String manufacturerComment) {
        this.manufacturerComment = manufacturerComment;
    }

    public String getManufacturerComment() {
        return manufacturerComment;
    }

    public void setIsPriceSatisfied(String isPriceSatisfied) {
        this.isPriceSatisfied = isPriceSatisfied;
    }

    public String getIsPriceSatisfied() {
        return isPriceSatisfied;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    public void setCustomerComment(String customerComment) {
        this.customerComment = customerComment;
    }

    public String getCustomerComment() {
        return customerComment;
    }

    public void setCloseComment(String closeComment) {
        this.closeComment = closeComment;
    }

    public String getCloseComment() {
        return closeComment;
    }


}