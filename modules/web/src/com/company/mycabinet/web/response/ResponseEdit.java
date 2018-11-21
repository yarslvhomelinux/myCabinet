package com.company.mycabinet.web.response;

import com.company.mycabinet.entity.State;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.company.mycabinet.entity.Response;
import com.haulmont.cuba.gui.components.GroupBoxLayout;

import javax.inject.Inject;
import java.util.List;

public class ResponseEdit extends AbstractEditor<Response> {

    @Inject
    protected DataManager dataManager;
    @Inject
    protected GroupBoxLayout closeCommentGroupBox;

    @Override
    public void ready() {
        super.ready();
        if (getItem().getRequest() == null || State.CUSTOMER_FEEDBACK_RECEIVED.equals(getItem().getRequest().getStatus())) {
            closeCommentGroupBox.setEnabled(true);
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

    @Override
    protected boolean postCommit(boolean committed, boolean close) {
        if (super.postCommit(committed, close)) {
            if (getItem().getRequest() != null) {
                getItem().getRequest().setStatus(State.RESPONSE_RECEIVED);
                List<Response> responseList = getItem().getRequest().getResponse();
                responseList.add(getItem());
                getItem().getRequest().setResponse(responseList);
                dataManager.commit(getItem().getRequest());
            }

            return true;
        }

        return false;
    }
}