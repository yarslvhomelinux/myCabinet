package com.company.mycabinet.utils;

import com.company.mycabinet.config.RolesConfig;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.entity.UserRole;

import javax.inject.Inject;

public class UserRoleUtils {

    @Inject
    protected RolesConfig rolesConfig;

    @Inject
    protected UserSessionSource userSessionSource;

    public boolean isCurrentUserAdmin() {
        if (userSessionSource.getUserSession().getCurrentOrSubstitutedUser() != null) {
            User user = userSessionSource.getUserSession().getCurrentOrSubstitutedUser();
            for (UserRole userRole : user.getUserRoles()) {
                if (rolesConfig.getAdminRole().equals(userRole.getRole().getId().toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCurrentUserCustomer() {
        if (userSessionSource.getUserSession().getCurrentOrSubstitutedUser() != null) {
            User user = userSessionSource.getUserSession().getCurrentOrSubstitutedUser();
            for (UserRole userRole : user.getUserRoles()) {
                if (rolesConfig.getCustomerRole().equals(userRole.getRole().getId().toString())) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isCurrentUserManufacturer() {
        if (userSessionSource.getUserSession().getCurrentOrSubstitutedUser() != null) {
            User user = userSessionSource.getUserSession().getCurrentOrSubstitutedUser();
            for (UserRole userRole : user.getUserRoles()) {
                if (rolesConfig.getManufacturerRole().equals(userRole.getRole().getId().toString())) {
                    return true;
                }
            }
        }

        return false;
    }
}
