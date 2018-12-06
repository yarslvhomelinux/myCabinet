package com.company.mycabinet.web.request;

import com.company.mycabinet.entity.State;
import com.company.mycabinet.service.UserUtilsService;
import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.PersistenceHelper;
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
    protected Button nextStageButton;
    @Inject
    protected Button improveButton;
    @Inject
    protected Button windowCommit;

    @Override
    protected void initNewItem(Request item) {
        super.initNewItem(item);
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

        if (State.CREATED.equals(getItem().getStatus())) {
            nextStageButton.setVisible(true);
        }

        if (State.ADMIN_PROCESSING.equals(getItem().getStatus()) && userUtilsService.isCurrentUserAdmin()) {
            improveButton.setVisible(true);
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

        item.setStatus(State.CREATED);
    }

    public void onNextStageButtonClick() {
        getItem().setStatus(State.ADMIN_PROCESSING);
        commitAndClose();
    }

    public void onImproveButtonClick() {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("request", getItem());
        paramsMap.put("userList", getItem().getManufacturer());
        openWindow("AssignmentManufacturerFrame", WindowManager.OpenType.DIALOG, paramsMap)
                .addCloseListener(e -> commitAndClose());
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
}