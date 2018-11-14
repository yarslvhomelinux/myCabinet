package com.company.mycabinet.web.request;

import com.company.mycabinet.entity.State;
import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.company.mycabinet.entity.Request;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.actions.CreateAction;

import javax.inject.Named;

public class RequestEdit extends AbstractEditor<Request> {

    @Named("attachmentsTable.create")
    protected CreateAction attachmentCreateAction;

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
    }

    protected void initNecessaryFields(Request item) {
        item.setRequestNumber(item.getId().toString().substring(0, 8));
        item.setStatus(State.CREATED);
    }

    public void onNextStageButtonClick() {
        getItem().setStatus(State.ADMIN_PROCESSING);
        commitAndClose();
    }

    public void onImproveButtonClick() {
        getItem().setStatus(State.MANUFACTURER_PROCESSING);
        openWindow("AssignmentManufacturerFrame", WindowManager.OpenType.DIALOG);
    }
}