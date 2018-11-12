package com.company.mycabinet.web.request;

import com.company.mycabinet.entity.State;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.company.mycabinet.entity.Request;

public class RequestEdit extends AbstractEditor<Request> {

    @Override
    protected void initNewItem(Request item) {
        super.initNewItem(item);
        initNecessaryFields(item);
    }

    @Override
    public void ready() {
        super.ready();

    }

    protected void initNecessaryFields(Request item) {
        item.setRequestNumber(item.getId().toString().substring(0,8));
        item.setStatus(State.CREATED);
    }
}