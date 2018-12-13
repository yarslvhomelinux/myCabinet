package com.company.mycabinet.web.extuser;

import com.company.mycabinet.service.UserUtilsService;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.Label;

import javax.inject.Inject;

public class SystemRules extends AbstractWindow {

    @Inject
    protected Label manufacturerRulesMain;
    @Inject
    protected Label customerRulesMain;
    @Inject
    protected UserUtilsService userUtilsService;

    @Override
    public void ready() {
        super.ready();

        if (userUtilsService.isCurrentUserAdmin()) {
            manufacturerRulesMain.setVisible(false);
            customerRulesMain.setVisible(false);
        } else if (userUtilsService.isCurrentUserManufacturer()) {
            manufacturerRulesMain.setVisible(true);
            customerRulesMain.setVisible(false);
        } else if (userUtilsService.isCurrentUserCustomer()) {
            manufacturerRulesMain.setVisible(false);
            customerRulesMain.setVisible(true);
        }
    }
}