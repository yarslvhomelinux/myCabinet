package com.company.mycabinet.web.response;

import com.company.mycabinet.entity.Response;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.GroupTable;

import javax.inject.Inject;

public class ResponseForCloseBrowse extends AbstractLookup {

    @Inject
    protected GroupTable<Response> responsesTable;

    public void onOpenRequestButtonClick() {
        if (responsesTable.getSingleSelected() != null && responsesTable.getSingleSelected().getRequest() != null) {
            openEditor(responsesTable.getSingleSelected().getRequest(), WindowManager.OpenType.THIS_TAB);
        }
    }
}