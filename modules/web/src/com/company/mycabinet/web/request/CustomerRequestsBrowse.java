package com.company.mycabinet.web.request;

import com.company.mycabinet.entity.Request;
import com.company.mycabinet.entity.Response;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Table;

import javax.inject.Inject;

public class CustomerRequestsBrowse extends AbstractLookup {

    @Inject
    protected GroupTable<Request> requestsTable;
    @Inject
    protected Metadata metadata;

    public void onCreateResponseClick() {
        if (requestsTable.getSingleSelected() != null) {
            Response response = metadata.create(Response.class);
            response.setRequest(requestsTable.getSingleSelected());
            openEditor(response, WindowManager.OpenType.NEW_TAB);
        } else {
            showNotification(getMessage("notSelectedRequestError"));
        }
    }
}