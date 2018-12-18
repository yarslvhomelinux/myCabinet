package com.company.mycabinet.web.request;

import com.company.mycabinet.entity.Request;
import com.company.mycabinet.entity.Response;
import com.company.mycabinet.entity.Status;
import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Table;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.Component;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.springframework.security.access.method.P;

public class CustomerRequestsBrowse extends AbstractLookup {

    @Inject
    protected GroupTable<Request> requestsTable;
    @Inject
    protected Metadata metadata;

    @Inject
    protected UserSessionSource userSessionSource;

    @Inject
    protected TimeSource timeSource;

    @Override
    public void ready() {
        super.ready();

        requestsTable.setStyleProvider((entity, property) -> {
            if (entity.getAssignDate() != null && "daysCount".equals(property)) {
                int daysCount = new Period(entity.getAssignDate().getTime(), timeSource.currentTimeMillis(), PeriodType.days()).getDays();
                int maxDaysForResponse = 2;
                if (daysCount > maxDaysForResponse)
                    return "back-red";
            }

            return null;
        });
    }

    public void onCreateResponseClick() {
        if (requestsTable.getSingleSelected() != null) {
            boolean responseAlreadyCreated = requestsTable.getSingleSelected().getResponse()
                    .stream()
                    .filter(response -> response.getCreator().equals(userSessionSource.getUserSession().getUser()))
                    .count() >= 1;
            if (!responseAlreadyCreated) {
                Response response = metadata.create(Response.class);
                response.setRequest(requestsTable.getSingleSelected());
                openEditor(response, WindowManager.OpenType.NEW_TAB);
            } else {
                showNotification("Вы уже создали отклик на эту заявку!");
            }
        } else {
            showNotification(getMessage("notSelectedRequestError"));
        }
    }

    public Component generateDaysCountCell(Request entity) {
        String daysCount = "";
        if (Status.MANUFACTURER_PROCESSING.equals(entity.getStatus())) {
            if (entity.getAssignDate() != null)
                daysCount = Integer.toString(new Period(timeSource.currentTimeMillis(), entity.getAssignDate().getTime(), PeriodType.days()).getDays());
        }

        return new Table.PlainTextCell(daysCount);
    }

    public void onCreateSpecifyBUttonClick() {
        if (requestsTable.getSingleSelected() != null) {
            boolean responseAlreadyCreated = requestsTable.getSingleSelected().getResponse()
                    .stream()
                    .filter(response -> response.getCreator().equals(userSessionSource.getUserSession().getUser()))
                    .count() >= 1;
            if (!responseAlreadyCreated) {
                Response response = metadata.create(Response.class);
                response.setRequest(requestsTable.getSingleSelected());
                openEditor(response, WindowManager.OpenType.NEW_TAB, ParamsMap.of("isSpecify", true));
            } else {
                showNotification("Вы уже создали отклик или уточнение на эту заявку!");
            }
        } else {
            showNotification(getMessage("notSelectedRequestError"));
        }
    }
}