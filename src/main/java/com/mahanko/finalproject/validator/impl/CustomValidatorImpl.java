package com.mahanko.finalproject.validator.impl;

import com.mahanko.finalproject.controller.ParametersType;
import com.mahanko.finalproject.validator.CustomValidator;

import java.util.Map;
import java.util.regex.Pattern;

public class CustomValidatorImpl implements CustomValidator {
    private static final String NAME_SURNAME_REGEX = "^[A-Za-zА-Яа-я]{3,45}$";
    private static final String LOGIN_REGEX = "^[\\w&&\\D]\\w{4,15}$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[\\w@$!%*?&]{8,20}$";

    @Override
    public boolean validateLogin(String login) {
        Pattern pattern = Pattern.compile(LOGIN_REGEX);
        return pattern.matcher(login).matches();
    }

    @Override
    public boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        return pattern.matcher(password).matches();
    }

    @Override
    public boolean validateName(String name) {
        Pattern pattern = Pattern.compile(NAME_SURNAME_REGEX);
        return pattern.matcher(name).matches();
    }

    @Override
    public boolean validateRegistration(Map<String, String> params) {
        boolean isValid = true;
        for (String value : params.values()) {
            if (value == null || value.isBlank()) {
                return false;
            }
        }

        String name = params.get(ParametersType.NAME.toString());
        String surname = params.get(ParametersType.SURNAME.toString());
        String login = params.get(ParametersType.LOGIN.toString());
        String password = params.get(ParametersType.PASSWORD.toString());
        String confirmPassword = params.get(ParametersType.CONFIRM_PASSWORD.toString());
        if (!validateName(name) ||
                !validateName(surname)) {
            isValid = false;
        }

        if (!validateLogin(login)) {
            isValid = false;
        }

        if (!validatePassword(password) ||
                !password.equals(confirmPassword)) {
            isValid = false;
        }

        return isValid;
    }
}
