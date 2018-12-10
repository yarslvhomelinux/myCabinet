package com.company.mycabinet.service;

import com.company.mycabinet.entity.ExtUser;
import com.company.mycabinet.entity.Request;
import com.company.mycabinet.entity.Response;
import com.google.common.base.Strings;
import com.haulmont.cuba.core.app.EmailService;
import com.haulmont.cuba.core.app.EmailerConfig;
import com.haulmont.cuba.core.global.EmailInfo;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

@Service(WorkflowEmailerService.NAME)
public class WorkflowEmailerServiceBean implements WorkflowEmailerService {

    @Inject
    protected EmailService emailService;
    @Inject
    protected EmailerConfig emailerConfig;

    @Override
    public void sendMessageAboutCreateRequestToAdmin(Request request) {
        EmailInfo emailInfo = new EmailInfo(
                emailerConfig.getAdminAddress(), // recipients
                "В системе 'ОптоКит' создана новая заявка. Согласуйте её.", // subject
                emailerConfig.getAdminAddress(), // the "from" address will be taken from the "cuba.email.fromAddress" app property
                "com/company/mycabinet/email/messageToAdminAboutRequestCreateTemplate.txt", // body template
                Collections.singletonMap("request", request) // template parameters
        );
        emailService.sendEmailAsync(emailInfo);
    }

    @Override
    public void sendMessageAboutApproveRequestToManufacturer(Request request, List<ExtUser> extUsers) {
        StringBuilder sb = new StringBuilder("");
        for (ExtUser user : extUsers) {
            sb.append(user.getEmail()).append(",");
        }
        if (sb.length() > 1)
            sb = new StringBuilder(sb.substring(0, sb.length() - 1));

        EmailInfo emailInfo = new EmailInfo(
                sb.toString(), // recipients
                "На вас назначена новая заявка в системе 'ОптоКит'. Обработайте её.", // subject
                emailerConfig.getAdminAddress(), // the "from" address will be taken from the "cuba.email.fromAddress" app property
                "com/company/mycabinet/email/messageToManufacturerAboutApproveNewRequest.txt", // body template
                Collections.singletonMap("request", request) // template parameters
        );
        emailService.sendEmailAsync(emailInfo);
    }

    @Override
    public void sendMessageAboutApproveRequestToCustomer(Request request) {
        if (request != null && request.getCreator() != null && !Strings.isNullOrEmpty(request.getCreator().getEmail())) {
            EmailInfo emailInfo = new EmailInfo(
                    request.getCreator().getEmail(), // recipients
                    "Ваша заявка обработана администратором", // subject
                    emailerConfig.getAdminAddress(), // the "from" address will be taken from the "cuba.email.fromAddress" app property
                    "com/company/mycabinet/email/messageToCustomerAboutApproveRequest.txt", // body template
                    Collections.singletonMap("request", request) // template parameters
            );
            emailService.sendEmailAsync(emailInfo);
        }
    }

    @Override
    public void sendMessageAboutCreateResponseToCustomer(Request request, Response response) {
        if (request != null && request.getCreator() != null && !Strings.isNullOrEmpty(request.getCreator().getEmail())) {
            EmailInfo emailInfo = new EmailInfo(
                    request.getCreator().getEmail() + ", " + emailerConfig.getAdminAddress(), // recipients
                    "На заявку назначен новый ответ", // subject
                    emailerConfig.getAdminAddress(), // the "from" address will be taken from the "cuba.email.fromAddress" app property
                    "com/company/mycabinet/email/messageToAdminAndCustomerAboutCreateResponse.txt", // body template
                    Collections.singletonMap("request", request) // template parameters
            );
            emailService.sendEmailAsync(emailInfo);
        }
    }

    @Override
    public void sendMessageAboutCreateResponseFeedback(Request request, Response response) {
        if (response != null && response.getCreator() != null && !Strings.isNullOrEmpty(response.getCreator().getEmail())) {
            EmailInfo emailInfo = new EmailInfo(
                    response.getCreator().getEmail() + ", " + emailerConfig.getAdminAddress(), // recipients
                    "На отклик создан ответ клиента", // subject
                    emailerConfig.getAdminAddress(), // the "from" address will be taken from the "cuba.email.fromAddress" app property
                    "com/company/mycabinet/email/messageToManufacturerAndAdminAboutCreateFeedback.txt", // body template
                    Collections.singletonMap("request", request) // template parameters
            );
            emailService.sendEmailAsync(emailInfo);
        }
    }
}