package com.company.mycabinet.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.FileDescriptor;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.ManyToOne;

@NamePattern("%s|attachment")
@Table(name = "MYCABINET_ATTACHMENT")
@Entity(name = "mycabinet$Attachment")
public class Attachment extends StandardEntity {
    private static final long serialVersionUID = -3837915489023645053L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ATTACHMENT_ID")
    protected FileDescriptor attachment;

    @Column(name = "STATE")
    protected String state;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REQUEST_ID")
    protected Request request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESPONSE_ID")
    protected Response response;

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }


    public void setRequest(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }


    public void setAttachment(FileDescriptor attachment) {
        this.attachment = attachment;
    }

    public FileDescriptor getAttachment() {
        return attachment;
    }

    public void setState(Status state) {
        this.state = state == null ? null : state.getId();
    }

    public Status getState() {
        return state == null ? null : Status.fromId(state);
    }


}