package com.company.mycabinet.web.response;

import com.company.mycabinet.entity.ExtUser;
import com.company.mycabinet.entity.Status;
import com.company.mycabinet.service.UserUtilsService;
import com.company.mycabinet.service.WorkflowEmailerService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.UserSessionSource;
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
    protected FieldGroup closeCommentFieldGroup,
            feedbackFieldGroup;
    @Inject
    protected Button sendToCustomerButton,
            agreeResponseButton,
            sendPositiveFeedbackButton,
            sendNegativeFeedbackButton,
            closeResponseButton,
            closeRequestButton,
            specifyButton,
            sendSpecifyToManufacturerButton;

    @Named("feedbackFieldGroup.contact")
    protected Field contactField;
    @Named("feedbackFieldGroup.customerComment")
    protected Field customerCommentField;
    @Named("feedbackFieldGroup.isPriceSatisfied")
    protected Field priceCommentField;

    @Inject
    protected UserSessionSource userSessionSource;

    @Inject
    protected UserUtilsService userUtilsService;
    @Inject
    protected WorkflowEmailerService workflowEmailerService;

    @Override
    protected void initNewItem(Response item) {
        super.initNewItem(item);
        item.setState(Status.RESPONSE_CREATED);
        item.setCreator((ExtUser) userSessionSource.getUserSession().getUser());
    }

    @Override
    public void ready() {
        super.ready();

        if (getItem().getRequest() != null && Status.MANUFACTURER_PROCESSING.equals(getItem().getRequest().getStatus())
                && getItem().getState() != null && (getItem().getState().equals(Status.RESPONSE_ADMIN_PROCESSING) ||
                getItem().getState().equals(Status.RESPONSE_SPECIFY_ADM_PROCESSING))
                && userUtilsService.isCurrentUserAdmin()) {
            priceCommentField.setRequired(false);
            contactField.setRequired(false);
            agreeResponseButton.setVisible(true);
        }

        if (getItem().getState() != null && Status.CUSTOMER_FEEDBACK_RECEIVED.equals(getItem().getState())
                && userUtilsService.isCurrentUserManufacturer()) {
            closeCommentFieldGroup.setEditable(true);
        }

        if (Status.RESPONSE_RECEIVED.equals(getItem().getState())
                && userUtilsService.isCurrentUserCustomer()) {
            sendPositiveFeedbackButton.setVisible(true);
            sendNegativeFeedbackButton.setVisible(true);
        }

        if (Status.CUSTOMER_FEEDBACK_RECEIVED.equals(getItem().getState())
                && userUtilsService.isCurrentUserManufacturer()) {
            closeResponseButton.setVisible(true);
        }

        if (getItem().getRequest() != null &&
                Status.RESPONSE_SPECIFY.equals(getItem().getState())
                && userUtilsService.isCurrentUserCustomer()) {
            sendSpecifyToManufacturerButton.setVisible(true);
        }

        if (getItem().getRequest() != null && (Status.RESPONSE_CREATED.equals(getItem().getState())
                || Status.RESPONSE_SPECIFY_GOT.equals(getItem().getState()))
                && userUtilsService.isCurrentUserManufacturer()) {
            specifyButton.setVisible(true);
            sendToCustomerButton.setVisible(true);
        }

        if (getItem().getRequest() != null && Status.MANUFACTURER_PROCESSING.equals(getItem().getRequest().getStatus())
                && Status.RESPONSE_CLOSED.equals(getItem().getState())
                && userUtilsService.isCurrentUserAdmin()) {
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


    public void onAgreeResponseButtonClick() {
        if (Status.RESPONSE_ADMIN_PROCESSING.equals(getItem().getState())) {
            getItem().setState(Status.RESPONSE_RECEIVED);
            workflowEmailerService.sendMessageAboutCreateResponseToCustomer(getItem().getRequest(), getItem());
        } else if (Status.RESPONSE_SPECIFY_ADM_PROCESSING.equals(getItem().getState())) {
            getItem().setState(Status.RESPONSE_SPECIFY);
            workflowEmailerService.sendMessageAboutCreateSpecifyToCustomer(getItem().getRequest(), getItem());
        }

        commitAndClose();
    }

    public void onSendToAdminAgree() {
        if (getItem().getRequest() != null) {
            getItem().setState(Status.RESPONSE_ADMIN_PROCESSING);
            List<Response> responseList = getItem().getRequest().getResponse();
            responseList.add(getItem());
            getItem().getRequest().setResponse(responseList);
            commitAndClose();
            workflowEmailerService.sendMessageAboutCreateResponseToAdmin(getItem().getRequest(), getItem());
        }
    }

    //todo иземнить оповещение о принятой заявке
    public void onSentFeedbackButtonClick() {
        if (getItem().getRequest() != null) {
            getItem().setState(Status.RESPONSE_AGREE);
            commitAndClose();
            //workflowEmailerService.sendMessageAboutCreateResponseFeedback(getItem().getRequest(), getItem());
        }
    }

    //todo иземнить оповещение об отвергнутой заявке
    public void onSendNegativeFeedbackButtonClick() {
        if (getItem().getRequest() != null) {
            getItem().setState(Status.RESPONSE_DISAGREE);
            commitAndClose();
            //workflowEmailerService.sendMessageAboutCreateResponseFeedback(getItem().getRequest(), getItem());
        }
    }

    public void onCloseResponseButtonClick() {
        if (getItem().getState() != null) {
            getItem().setState(Status.RESPONSE_CLOSED);
            commitAndClose();
        }
    }

    public void onCloseRequestButtonClick() {
        if (getItem().getRequest() != null) {
            getItem().getRequest().setStatus(Status.REQUEST_CLOSED);
            dataManager.commit(getItem().getRequest());
            commitAndClose();
        }
    }

    //todo необходимо оповещение для админа
    public void onSpecifyButtonClick() {
        if (getItem().getState() != null) {
            getItem().setState(Status.RESPONSE_SPECIFY_ADM_PROCESSING);
            commitAndClose();
        }
    }

    public void onSendSpecifyToManufacturerButtonClick() {
        if (getItem().getState() != null) {
            getItem().setState(Status.RESPONSE_SPECIFY_GOT);
            commitAndClose();
        }
    }
}