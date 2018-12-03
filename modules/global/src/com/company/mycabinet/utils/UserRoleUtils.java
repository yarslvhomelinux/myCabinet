package com.company.mycabinet.utils;

import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.security.entity.Role;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.entity.UserRole;

import javax.inject.Inject;

public class UserUtils {

    @Inject
    protected UserSessionSource userSessionSource;

    public boolean isCurrentUserAdmin() {
        if (userSessionSource.getUserSession().getCurrentOrSubstitutedUser() != null) {
            User user = userSessionSource.getUserSession().getCurrentOrSubstitutedUser();
            for (UserRole userRole : user.getUserRoles()) {
                if (userRole.getRole())
            }
        }
    }
}
