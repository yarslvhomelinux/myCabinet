package com.company.mycabinet.web.extuser;

import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.app.security.user.browse.UserBrowser;
import com.haulmont.cuba.gui.components.GroupTable;

import javax.inject.Inject;

public class ExtUserBrowser extends UserBrowser {

    @Inject
    protected GroupTable usersTable;

    public void onFullEditButtonClick() {
        if (usersTable.getSingleSelected() != null) {
            openEditor("mycabinet$CustomExtUser.edit", usersTable.getSingleSelected(), WindowManager.OpenType.NEW_TAB);
        }
    }
}