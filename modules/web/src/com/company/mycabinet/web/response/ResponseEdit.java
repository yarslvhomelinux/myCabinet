package com.company.mycabinet.web.response;

import com.company.mycabinet.entity.ExtUser;
import com.company.mycabinet.entity.Status;
import com.company.mycabinet.service.UserUtilsService;
import com.company.mycabinet.service.WorkflowEmailerService;
import com.google.common.base.Strings;
import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.company.mycabinet.entity.Response;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import org.apache.commons.lang.StringUtils;

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

    @Inject
    protected GroupBoxLayout feedbackGroupBox;

    @Named("feedbackFieldGroup.contact")
    protected Field contactField;
    @Named("feedbackFieldGroup.customerComment")
    protected Field customerCommentField;
    @Named("feedbackFieldGroup.isPriceSatisfied")
    protected Field priceCommentField;

    @Named("responseMainInfoFieldGroup.price")
    protected Field priceField;
    @Named("responseMainInfoFieldGroup.deliveryPrice")
    protected Field deliveryPriceField;
    @Named("responseMainInfoFieldGroup.manufacturerComment")
    protected Field manufacturerCommentField;
    @Named("responseMainInfoFieldGroup.manufacturerInfo")
    protected Field manufacturerInfoField;

    @Named("attachmentTable.create")
    private CreateAction attachmentCreateAction;

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

        if (getContext().getParams().containsKey("isSpecify")) {
            priceField.setEditable(false);
            deliveryPriceField.setEditable(false);
            manufacturerCommentField.setRequired(true);
            manufacturerInfoField.setRequired(false);
        }

        boolean activeResponse = getItem().getRequest() != null && Status.MANUFACTURER_PROCESSING.equals(getItem().getRequest().getStatus());

        if (activeResponse && getItem().getState() != null && (getItem().getState().equals(Status.RESPONSE_ADMIN_PROCESSING) ||
                getItem().getState().equals(Status.RESPONSE_SPECIFY_ADM_PROCESSING))
                && userUtilsService.isCurrentUserAdmin()) {
            priceCommentField.setRequired(false);
            contactField.setRequired(false);
            agreeResponseButton.setVisible(true);
            priceField.setRequired(false);
            deliveryPriceField.setRequired(false);
        }

        if (activeResponse && getItem().getState() != null && (getItem().getState().equals(Status.RESPONSE_AGREE) ||
                getItem().getState().equals(Status.RESPONSE_DISAGREE))
                && userUtilsService.isCurrentUserManufacturer()) {
            feedbackGroupBox.setVisible(false);
        }

        if (activeResponse && getItem().getState() != null && Status.RESPONSE_SPECIFY.equals(getItem().getState())
                && userUtilsService.isCurrentUserCustomer()) {
            priceCommentField.setRequired(false);
            customerCommentField.setRequired(true);
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

        attachmentCreateAction.setBeforeActionPerformedHandler(() -> {
            if (PersistenceHelper.isNew(getItem()))
                commit();

            return true;
        });

        attachmentCreateAction.setWindowParams(ParamsMap.of("response", getItem()));
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

    public void onSentPositiveFeedbackButtonClick() {
        if (getItem().getRequest() != null) {
            getItem().setState(Status.RESPONSE_AGREE);
            commitAndClose();
            workflowEmailerService.sendMessageAboutCreatePositiveResponseFeedback(getItem().getRequest(), getItem());
        }
    }

    public void onSendNegativeFeedbackButtonClick() {
        if (getItem().getRequest() != null && StringUtils.isNotEmpty(getItem().getCustomerComment())) {
            getItem().setState(Status.RESPONSE_DISAGREE);
            commitAndClose();
            workflowEmailerService.sendMessageAboutCreateNegativeResponseFeedback(getItem().getRequest(), getItem());
        } else {
            showNotification("Пожалуйста заполните причину отказа в комментарии");
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

    public void onSpecifyButtonClick() {
        if (getItem().getState() != null && !Strings.isNullOrEmpty(getItem().getManufacturerComment())) {
            priceField.setRequired(false);
            deliveryPriceField.setRequired(false);
            getItem().setState(Status.RESPONSE_SPECIFY_ADM_PROCESSING);
            commitAndClose();
            workflowEmailerService.sendMessageAboutSpecifyRequestToAdmin(getItem().getRequest(), getItem());
        } else {
            showNotification("Пожалуйста заполните комментарий");
        }
    }

    public void onSendSpecifyToManufacturerButtonClick() {
        if (getItem().getState() != null) {
            getItem().setState(Status.RESPONSE_SPECIFY_GOT);
            workflowEmailerService.sendMessageAboutSpecifyGotToManufacturer(getItem().getRequest(), getItem());
            commitAndClose();
        }
    }
}