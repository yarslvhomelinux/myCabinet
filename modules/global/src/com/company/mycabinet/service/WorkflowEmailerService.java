package com.company.mycabinet.service;


import com.company.mycabinet.entity.ExtUser;
import com.company.mycabinet.entity.Request;
import com.company.mycabinet.entity.Response;

import java.util.List;

public interface WorkflowEmailerService {
    String NAME = "mycabinet_WorkflowEmailerService";

    void sendMessageAboutCreateRequestToAdmin(Request request);

    void sendMessageAboutApproveRequestToManufacturer(Request request, List<ExtUser> extUser);

    void sendMessageAboutApproveRequestToCustomer(Request request);

    void sendMessageAboutCreateResponseToCustomer(Request request, Response response);

    void sendMessageAboutCreateResponseToAdmin(Request request, Response response);

    void sendMessageAboutCreateSpecifyToCustomer(Request request, Response response);

    void sendMessageAboutCreateResponseFeedback(Request request, Response response);
}