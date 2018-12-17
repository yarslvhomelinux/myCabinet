package com.company.mycabinet.web.request;

import com.company.mycabinet.entity.ExtUser;
import com.company.mycabinet.entity.Status;
import com.company.mycabinet.service.UserUtilsService;
import com.company.mycabinet.service.WorkflowEmailerService;
import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.company.mycabinet.entity.Request;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.components.actions.RemoveAction;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

public class RequestEdit extends AbstractEditor<Request> {

    @Named("attachmentsTable.create")
    protected CreateAction attachmentCreateAction;
    @Named("attachmentsTable.edit")
    protected EditAction attachmentEditAction;
    @Named("attachmentsTable.remove")
    protected RemoveAction attachmentRemoveAction;

    @Inject
    protected DataManager dataManager;
    @Inject
    protected UserUtilsService userUtilsService;
    @Inject
    protected WorkflowEmailerService workflowEmailerService;

    @Named("fieldGroupRight.contactPerson")
    protected Field contactPerson;
    @Named("fieldGroupRight.contactPersonPhone")
    protected Field contactPersonPhone;

    @Inject
    protected FieldGroup fieldGroup;
    @Inject
    protected FieldGroup fieldGroupRight;

    @Inject
    protected GroupBoxLayout responsesGroupBox;

    @Inject
    protected Button nextStageButton,
            closeRequest,
            improveButton,
            windowCommit;

    @Inject
    protected UserSessionSource userSessionSource;

    @Inject
    protected TimeSource timeSource;

    protected boolean itemCreated = false;

    @Override
    protected void initNewItem(Request item) {
        super.initNewItem(item);
        itemCreated = true;
        initNecessaryFields(item);
    }

    @Override
    public void ready() {
        super.ready();
        attachmentCreateAction.setBeforeActionPerformedHandler(new Action.BeforeActionPerformedHandler() {
            @Override
            public boolean beforeActionPerformed() {
                if (PersistenceHelper.isNew(getItem()))
                    commit();

                return true;
            }
        });

        attachmentCreateAction.setWindowParams(ParamsMap.of("status", getItem().getStatus()));

        if (Status.REQUEST_CREATED.equals(getItem().getStatus())) {
            nextStageButton.setVisible(true);
            responsesGroupBox.setVisible(false);
        }

        if (Status.REQUEST_ADMIN_PROCESSING.equals(getItem().getStatus()) && userUtilsService.isCurrentUserAdmin()) {
            improveButton.setVisible(true);
        }

        if (Status.MANUFACTURER_PROCESSING.equals(getItem().getStatus()) && (userUtilsService.isCurrentUserAdmin()
                || userUtilsService.isCurrentUserCustomer())) {
            closeRequest.setVisible(true);
        }

        if (userUtilsService.isCurrentUserManufacturer()) {
            contactPerson.setVisible(false);
            contactPersonPhone.setVisible(false);
            fieldGroupRight.setEditable(false);
            fieldGroup.setEditable(false);
            responsesGroupBox.setVisible(false);
            attachmentCreateAction.setEnabled(false);
            attachmentEditAction.setEnabled(true);
            attachmentRemoveAction.setEnabled(false);
            windowCommit.setEnabled(false);
        }
    }

    protected void initNecessaryFields(Request item) {
        item.setStatus(Status.REQUEST_CREATED);
        item.setCreator((ExtUser) userSessionSource.getUserSession().getCurrentOrSubstitutedUser());
    }

    public void onNextStageButtonClick() {
        getItem().setStatus(Status.REQUEST_ADMIN_PROCESSING);
        commitAndClose();
        if (itemCreated)
            workflowEmailerService.sendMessageAboutCreateRequestToAdmin(getItem());
    }

    public void onImproveButtonClick() {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("request", getItem());
        paramsMap.put("userList", getItem().getManufacturer());
        openWindow("AssignmentManufacturerFrame", WindowManager.OpenType.DIALOG, paramsMap)
                .addCloseListener(e -> {
                    getItem().setAssignDate(timeSource.currentTimestamp());
                    commitAndClose();
                    workflowEmailerService.sendMessageAboutApproveRequestToManufacturer(getItem(), getItem().getManufacturer());
                    workflowEmailerService.sendMessageAboutApproveRequestToCustomer(getItem());
                });
    }

    @Override
    protected boolean preCommit() {
        if (super.preCommit()) {
            if (getItem().getRequestNumber() == null && getItem().getProductCategory() != null) {
                LoadContext<Request> loadContext = new LoadContext<>(Request.class);
                loadContext.setQuery(new LoadContext.Query("select e from mycabinet$Request e"));

                getItem().setRequestNumber(Integer.toOctalString(dataManager.loadList(loadContext).size() + 1));

                getItem().setRequestNumber(getItem().getRequestNumber() + getItem().getProductCategory().getCode());
                return true;
            }
            return true;
        }

        return false;
    }

    public void onCloseRequestClick() {
        if (getItem().getStatus() != null) {
            getItem().setStatus(Status.REQUEST_CLOSED);
            commitAndClose();
        }
    }
}