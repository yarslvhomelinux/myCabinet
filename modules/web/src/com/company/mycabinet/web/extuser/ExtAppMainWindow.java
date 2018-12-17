package com.company.mycabinet.web.extuser;

import com.company.mycabinet.entity.ExtUser;
import com.company.mycabinet.entity.Request;
import com.company.mycabinet.entity.Response;
import com.company.mycabinet.service.UserUtilsService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
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
    protected Label mainLabelCustomer,
            counfOfRequestForAgree,
            endLabelCustomer,
            mainLabelManufacturer,
            countOfResponsesForAgree,
            countOfResponsForSpecifyAgree,
            countOfNewResponses,
            countOfActiveResponses;

    @Inject
    protected Button viewRequestButton;

    @Inject
    protected VBoxLayout allInfo,
            adminTasksInfo,
            customerTasksInfo,
            manufacturerTasksInfo;
    @Inject
    protected UserSessionSource userSessionSource;
    @Inject
    protected UserUtilsService userUtilsService;

    @Inject
    protected DataManager dataManager;

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

        initCountsTasks();
    }

    protected void initCountsTasks() {
        if (userUtilsService.isCurrentUserAdmin()) {
            adminTasksInfo.setVisible(true);
            LoadContext lcRequests = new LoadContext<>(Request.class);
            lcRequests.setQuery(new LoadContext.Query("select e from mycabinet$Request e where e.status = 'requestAdminProcessing'"));

            int num = dataManager.loadList(lcRequests).size();
            counfOfRequestForAgree.setValue(num);

            LoadContext lcResponses = new LoadContext<>(Response.class);
            lcResponses.setQuery(new LoadContext.Query("select e from mycabinet$Response e where e.state = 'responseAdminProcessing'"));

            int numResponses = dataManager.loadList(lcResponses).size();
            countOfResponsesForAgree.setValue(numResponses);

            LoadContext lcResponsesSpecify = new LoadContext<>(Response.class);
            lcResponsesSpecify.setQuery(new LoadContext.Query("select e from mycabinet$Response e where e.state = 'responseSpecifyAdminProcessing'"));

            int numResponsesSpecify = dataManager.loadList(lcResponsesSpecify).size();
            countOfResponsForSpecifyAgree.setValue(numResponsesSpecify);
        } else if (userUtilsService.isCurrentUserCustomer()) {
            customerTasksInfo.setVisible(true);

            LoadContext lcResponses = new LoadContext<>(Response.class);
            lcResponses.setQuery(new LoadContext.Query("select e from mycabinet$Response e where e.state = 'responseReceived' " +
                    "and e.request.createdBy = '" + userSessionSource.getUserSession().getUser().getLogin() + "'"));

            int numResponses = dataManager.loadList(lcResponses).size();
            countOfNewResponses.setValue(numResponses);
        } else if (userUtilsService.isCurrentUserManufacturer()) {
            manufacturerTasksInfo.setVisible(true);

            LoadContext lcResponses = new LoadContext<>(Response.class);
            lcResponses.setQuery(new LoadContext.Query("select e from mycabinet$Response e where (e.state = 'customerFeedbackReveiced' " +
                    "or e.state = 'responseSpecifyGot')" +
                    "and e.createdBy = '" + userSessionSource.getUserSession().getUser().getLogin() + "'"));

            int numResponses = dataManager.loadList(lcResponses).size();
            countOfActiveResponses.setValue(numResponses);
        }
    }

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