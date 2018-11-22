package com.company.mycabinet.web.extuser;

import com.company.mycabinet.entity.ExtUser;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.web.app.mainwindow.AppMainWindow;

import javax.inject.Inject;

public class ExtAppMainWindow extends AppMainWindow {

    @Inject
    protected UserSessionSource userSessionSource;

    public void onEditCurrentUserButtonClick() {
        openEditor("mycabinet$ExtUserSelf.edit", (ExtUser) userSessionSource.getUserSession().getUser(), WindowManager.OpenType.NEW_TAB);
    }
}