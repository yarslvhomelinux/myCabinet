package com.company.mycabinet.web.attachment;

import com.company.mycabinet.entity.Request;
import com.company.mycabinet.entity.Response;
import com.company.mycabinet.entity.Status;
import com.haulmont.cuba.gui.WindowParam;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.company.mycabinet.entity.Attachment;

import javax.inject.Inject;

public class AttachmentEdit extends AbstractEditor<Attachment> {

    @WindowParam
    private Request request;
    @WindowParam
    private Response response;

    @Override
    protected void initNewItem(Attachment item) {
        super.initNewItem(item);

        if (getContext().getParams() != null && getContext().getParams().get("status") != null)
            item.setState((Status) getContext().getParams().get("status"));

        if (request != null) {
            item.setRequest(request);
        }

        if (response != null) {
            item.setResponse(response);
        }
    }
}