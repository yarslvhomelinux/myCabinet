package com.company.mycabinet.web.request;

import com.company.mycabinet.entity.ExtUser;
import com.company.mycabinet.entity.Request;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.TwinColumn;
import org.apache.commons.collections4.CollectionUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class AssignmentManufacturerFrame extends AbstractWindow {

    @Inject
    protected TwinColumn twinColumn;
    @Inject
    protected DataManager dataManager;

    @Override
    public void ready() {
        super.ready();
        if (getContext().getParams().get("userList") != null) {
            List<ExtUser> extUserList = (List<ExtUser>) getContext().getParams().get("userList");
            twinColumn.setValue(extUserList);
        }
    }

    public void onCancelButtonClick() {
        close(CLOSE_ACTION_ID);
    }

    public void onOkButtonClick() {
        if (getContext().getParams().get("request") != null) {
            Request request = (Request) getContext().getParams().get("request");
            //request = dataManager.reload(request, "request-view");
            List<ExtUser> users = new ArrayList<>(twinColumn.getValue());
            if (CollectionUtils.isNotEmpty(users)) {
                request.setManufacturer(users);
                close(CLOSE_ACTION_ID);
            } else {
                showNotification(getMessage("selectUserError"));
            }
        }
    }
}