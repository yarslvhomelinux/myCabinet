package com.company.mycabinet.web.screens;

import com.company.mycabinet.entity.UserType;
import com.company.mycabinet.service.RegistrationService;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.web.app.loginwindow.AppLoginWindow;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class ExtAppLoginWindow extends AppLoginWindow {

    @Inject
    protected LinkButton registerLinkButton,
            loginLinkButton;

    @Inject
    protected RegistrationService registrationService;
    @Inject
    protected DateField birthDateField;
    @Inject
    protected HBoxLayout manufacturerHBox,
            customerHBox;
    @Inject
    protected CheckBox customerCheckBox,
            manufacturerCheckBox;

    @Inject
    protected LookupField businessCategoryLookupField;

    @Inject
    protected Action submit;
    @Inject
    protected TextField firstNameTextField,
            middleNameTextField,
            surNameTextField,
            phoneNumberTextField,
            mailTextField,
            organizationNameTextField,
            activityTypeTextField,
            legalAdressTextField,
            actualAdressTextField,
            manufacturerLegalAddressTextField,
            firmAgeTextField,
            productionVolumeTextField,
            goodsCategory;

    @Inject
    protected Label surnameLabel,
            firstNameLabel,
            middleNameLabel,
            birthDateLabel,
            phoneNumberLabel,
            mailLabel,
            orgNameLabel,
            activityTypeLabel,
            legalAddressLabel,
            actualAddressLabel,
            businessCategory,
            manufacturerLegalAddressLabel,
            firmAgeLabel,
            productionVolumeLabel,
            goodsCategoryLabel;

    @Inject
    protected Button loginButton;

    @Override
    public void ready() {
        super.ready();
        performChooseUserStatus();
        performChooseRegistrationOrLogin();
    }

    protected void performChooseUserStatus() {
        customerCheckBox.setValue(true);
        customerCheckBox.addValueChangeListener(e -> {
            if ((boolean) e.getValue()) {
                manufacturerCheckBox.setValue(false);
                setVisibleCustomerRegistrationFields(true);
                setVisibleManufacturerRegistrationField(false);
            }
        });

        manufacturerCheckBox.addValueChangeListener(e -> {
            if ((boolean) e.getValue()) {
                customerCheckBox.setValue(false);
                setVisibleCustomerRegistrationFields(false);
                setVisibleManufacturerRegistrationField(true);
            }
        });
    }

    protected void performChooseRegistrationOrLogin() {
        loginLinkButton.setAction(new AbstractAction("goToLoginAction") {
            @Override
            public void actionPerform(Component component) {
                performSignInAction();

            }
        });

        loginLinkButton.setVisible(false);

        registerLinkButton.setAction(new AbstractAction("goToRegisterAction") {
            @Override
            public void actionPerform(Component component) {
                setVisibleCustomerRegistrationFields(true);
                setVisibleManufacturerRegistrationField(false);
                setVisibleUserStatusChoise(true);
                manufacturerHBox.setVisible(true);
                customerHBox.setVisible(true);
                firstNameLabel.setVisible(true);
                firstNameTextField.setVisible(true);
                middleNameLabel.setVisible(true);
                middleNameTextField.setVisible(true);
                surnameLabel.setVisible(true);
                surNameTextField.setVisible(true);
                customerCheckBox.setValue(true);
                mailTextField.setVisible(true);
                mailLabel.setVisible(true);
                manufacturerCheckBox.setValue(false);
                loginLinkButton.setVisible(true);
                registerLinkButton.setVisible(false);

                loginButton.setAction(new AbstractAction("registerAction") {
                    @Override
                    public void actionPerform(Component component) {
                        if (areRequiredFieldsFilled()) {
                            if (registrationService.isUniqueUserLogin(loginField.getValue())) {
                                registerNewUser();
                                showNotification(getMessage(getMessage("seccessfullyMessage")));
                                performSignInAction();
                                loginField.setValue(null);
                                passwordField.setValue(null);
                            } else
                                showNotification(getMessage("duplicateLoginError"));
                        } else
                            showNotification(getMessage("requiredFieldsDoNotFilled"));
                    }
                });
            }
        });
    }

    protected void performSignInAction() {
        setVisibleCustomerRegistrationFields(false);
        setVisibleManufacturerRegistrationField(false);
        setVisibleUserStatusChoise(false);
        manufacturerHBox.setVisible(false);
        customerHBox.setVisible(false);
        registerLinkButton.setVisible(true);
        loginLinkButton.setVisible(false);
        mailTextField.setVisible(false);
        mailLabel.setVisible(false);
        firstNameTextField.setVisible(false);
        firstNameLabel.setVisible(false);
        middleNameTextField.setVisible(false);
        middleNameLabel.setVisible(false);
        surNameTextField.setVisible(false);
        surnameLabel.setVisible(false);
        loginButton.setAction(submit);
    }

    protected boolean areRequiredFieldsFilled() {
        if (loginField.getValue() != null && passwordField.getValue() != null && mailTextField.getValue() != null)
            return true;
        else
            return false;
    }

    protected void registerNewUser() {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("login", loginField.getValue());
        paramsMap.put("password", passwordField.getValue());
        paramsMap.put("firstName", firstNameTextField.getValue());
        paramsMap.put("middleName", middleNameTextField.getValue());
        paramsMap.put("surName", surNameTextField.getValue());
        paramsMap.put("birthday", birthDateField.getValue());
        paramsMap.put("orgName", organizationNameTextField.getValue());
        paramsMap.put("activityName", activityTypeTextField.getValue());
        paramsMap.put("legalAddress", legalAdressTextField.getValue());
        paramsMap.put("actualAddress", actualAdressTextField.getValue());
        paramsMap.put("businessCategory", businessCategoryLookupField.getValue());

        if (customerCheckBox.getValue())
            paramsMap.put("userType", UserType.CUSTOMER);
        else if (manufacturerCheckBox.getValue())
            paramsMap.put("userType", UserType.MANUFACTURER);

        paramsMap.put("firmAge", firmAgeTextField.getValue());
        paramsMap.put("productVolume", productionVolumeTextField.getValue());
        paramsMap.put("goodsCategory", goodsCategory.getValue());

        registrationService.registerStandartUser(paramsMap);
    }

    protected void setVisibleCustomerRegistrationFields(boolean isVisible) {
        birthDateField.setVisible(isVisible);
        birthDateLabel.setVisible(isVisible);
        phoneNumberTextField.setVisible(isVisible);
        phoneNumberLabel.setVisible(isVisible);
        //mailTextField.setVisible(isVisible);
        organizationNameTextField.setVisible(isVisible);
        orgNameLabel.setVisible(isVisible);
        activityTypeTextField.setVisible(isVisible);
        activityTypeLabel.setVisible(isVisible);
        legalAdressTextField.setVisible(isVisible);
        legalAddressLabel.setVisible(isVisible);
        actualAdressTextField.setVisible(isVisible);
        actualAddressLabel.setVisible(isVisible);
        businessCategoryLookupField.setVisible(isVisible);
        businessCategory.setVisible(isVisible);
    }

    protected void setVisibleManufacturerRegistrationField(boolean isVisible) {
        //contactNameTextField.setVisible(isVisible);
        phoneNumberTextField.setVisible(isVisible);
        phoneNumberLabel.setVisible(isVisible);
        //mailTextField.setVisible(isVisible);
        manufacturerLegalAddressTextField.setVisible(isVisible);
        manufacturerLegalAddressLabel.setVisible(isVisible);
        firmAgeTextField.setVisible(isVisible);
        firmAgeLabel.setVisible(isVisible);
        productionVolumeTextField.setVisible(isVisible);
        productionVolumeLabel.setVisible(isVisible);
        goodsCategory.setVisible(isVisible);
        goodsCategory.setVisible(isVisible);
    }

    protected void setVisibleUserStatusChoise(boolean isVisible) {
        //userTypeGroupBox.setVisible(isVisible);
    }
}
