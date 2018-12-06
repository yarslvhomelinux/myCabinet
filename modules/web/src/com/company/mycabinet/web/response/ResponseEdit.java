package com.company.mycabinet.web.response;

import com.company.mycabinet.entity.State;
import com.company.mycabinet.service.UserUtilsService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.company.mycabinet.entity.Response;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

public class ResponseEdit extends AbstractEditor<Response> {

    @Inject
    protected DataManager dataManager;
    @Inject
    protected FieldGroup closeCommentFieldGroup;
    @Inject
    protected Button sendToCustomerButton;
    @Inject
    protected Button sentFeedbackButton;
    @Inject
    protected Button closeResponseButton;
    @Inject
    protected Button closeRequestButton;

    @Named("feedbackFieldGroup.contact")
    protected Field contactField;
    @Named("feedbackFieldGroup.customerComment")
    protected Field customerCommentField;

    @Inject
    protected UserUtilsService userUtilsService;

    @Override
    protected void initNewItem(Response item) {
        super.initNewItem(item);
        item.setState(State.RESPONSE_CREATED);
    }

    @Override
    public void ready() {
        super.ready();

        if (getItem().getRequest() != null && State.MANUFACTURER_PROCESSING.equals(getItem().getRequest().getStatus())
                && getItem().getState() != null && getItem().getState().equals(State.RESPONSE_CREATED)
                && userUtilsService.isCurrentUserManufacturer()) {
            sendToCustomerButton.setVisible(true);
        }

        if (getItem().getState() != null && State.CUSTOMER_FEEDBACK_RECEIVED.equals(getItem().getState())
                && userUtilsService.isCurrentUserManufacturer()) {
            closeCommentFieldGroup.setEditable(true);
        }

        if (getItem().getRequest() != null && State.RESPONSE_RECEIVED.equals(getItem().getState())
                && userUtilsService.isCurrentUserCustomer()) {
            sentFeedbackButton.setVisible(true);
        }

        if (getItem().getRequest() != null && State.CUSTOMER_FEEDBACK_RECEIVED.equals(getItem().getState())
                && userUtilsService.isCurrentUserManufacturer()) {
            closeResponseButton.setVisible(true);
        }

        if (getItem().getRequest() != null && State.MANUFACTURER_PROCESSING.equals(getItem().getRequest().getStatus())
                &&  State.RESPONSE_CLOSED.equals(getItem().getState())
                && userUtilsService.isCurrentUserCustomer()) {
            closeRequestButton.setVisible(true);
        }

        if (userUtilsService.isCurrentUserManufacturer()) {
            customerCommentField.setVisible(false);
            contactField.setVisible(false);
        }
    }

    public void onOpenRequestEditorButtonClick() {
        if (getItem().getRequest() != null) {
            openEditor(getItem().getRequest(), WindowManager.OpenType.NEW_TAB);
        } else {
            showNotification(getMessage("requestIsNull"));
        }
    }

    public void onOpenRequestBrowserButtonClick() {
        openWindow("mycabinet$CustomerRequests.browse", WindowManager.OpenType.NEW_TAB);
    }

    public void onSendToCustomerButtonClick() {
        if (getItem().getRequest() != null) {
            getItem().setState(State.RESPONSE_RECEIVED);
            List<Response> responseList = getItem().getRequest().getResponse();
            responseList.add(getItem());
            getItem().getRequest().setResponse(responseList);
            //dataManager.commit(getItem().getRequest());
            commitAndClose();
        }
    }

    public void onSentFeedbackButtonClick() {
        if (getItem().getRequest() != null) {
            getItem().setState(State.CUSTOMER_FEEDBACK_RECEIVED);
            commitAndClose();
        }
    }

    public void onCloseResponseButtonClick() {
        if (getItem().getState() != null) {
            getItem().setState(State.RESPONSE_CLOSED);
            commitAndClose();
        }
    }

    public void onCloseRequestButtonClick() {
        if (getItem().getRequest() != null) {
            getItem().getRequest().setStatus(State.REQUEST_CLOSED);
            dataManager.commit(getItem().getRequest());
            commitAndClose();
        }
    }
}