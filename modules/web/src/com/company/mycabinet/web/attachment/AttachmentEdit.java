package com.company.mycabinet.web.attachment;

import com.company.mycabinet.entity.State;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.company.mycabinet.entity.Attachment;

public class AttachmentEdit extends AbstractEditor<Attachment> {

    @Override
    protected void initNewItem(Attachment item) {
        super.initNewItem(item);
        if (getContext().getParams() != null && getContext().getParams().get("status") != null)
            item.setState((State) getContext().getParams().get("status"));
    }
}