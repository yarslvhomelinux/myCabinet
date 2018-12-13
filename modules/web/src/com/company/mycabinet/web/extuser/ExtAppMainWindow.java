package com.company.mycabinet.web.extuser;

import com.company.mycabinet.entity.ExtUser;
import com.company.mycabinet.service.UserUtilsService;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.HBoxLayout;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.VBoxLayout;
import com.haulmont.cuba.web.app.mainwindow.AppMainWindow;

import javax.inject.Inject;

public class ExtAppMainWindow extends AppMainWindow {

    @Inject
    protected Label mainLabelCustomer;
    @Inject
    protected Label endLabelCustomer;
    @Inject
    protected Label mainLabelManufacturer;

    @Inject
    protected Button viewRequestButton;

    @Inject
    protected VBoxLayout allInfo;

    @Inject
    protected UserUtilsService userUtilsService;

    @Override
    public void ready() {
        super.ready();

        if (userUtilsService.isCurrentUserCustomer()) {
            allInfo.setVisible(true);
            viewRequestButton.setVisible(true);
            endLabelCustomer.setVisible(true);
            mainLabelCustomer.setVisible(true);

            mainLabelManufacturer.setVisible(false);
        } else if (userUtilsService.isCurrentUserManufacturer()) {
            viewRequestButton.setVisible(false);
            endLabelCustomer.setVisible(false);
            mainLabelCustomer.setVisible(false);

            allInfo.setVisible(true);
            mainLabelManufacturer.setVisible(true);
        } else if (userUtilsService.isCurrentUserAdmin()) {
            allInfo.setVisible(false);
        }
    }

    @Inject
    protected UserSessionSource userSessionSource;

    public void onEditCurrentUserButtonClick() {
        openEditor("mycabinet$ExtUserSelf.edit", (ExtUser) userSessionSource.getUserSession().getUser(), WindowManager.OpenType.NEW_TAB);
    }

    public void onViewRequestButtonClick() {
        openWindow("mycabinet$MyRequest.browse", WindowManager.OpenType.NEW_TAB);
    }

    public void onViewRulesButtonClick() {
        openWindow("systemRulesWindow", WindowManager.OpenType.NEW_TAB);
    }
}