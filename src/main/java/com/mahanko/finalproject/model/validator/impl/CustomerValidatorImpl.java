package com.mahanko.finalproject.model.validator.impl;

import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.model.validator.CustomerValidator;
import com.mysql.cj.util.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import java.util.regex.Pattern;

/**
 * The type CustomerValidatorImpl class. Perform customers information validation.
 */
public class CustomerValidatorImpl implements CustomerValidator {
    private static final String NAME_SURNAME_REGEX = "^[A-Za-zА-Яа-я]{3,45}$";
    private static final String LOGIN_REGEX = "^[\\w&&\\D]\\w{4,15}$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[\\w@$!%*?&]{8,20}$";

    @Override
    public boolean validateLogin(String login) {
        Pattern pattern = Pattern.compile(LOGIN_REGEX);
        return !StringUtils.isEmptyOrWhitespaceOnly(login)
                && pattern.matcher(login).matches();
    }

    @Override
    public boolean validatePassword(String password, String confirmPassword) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        return !StringUtils.isEmptyOrWhitespaceOnly(password)
                && pattern.matcher(password).matches()
                && password.equals(confirmPassword);
    }

    @Override
    public boolean validateName(String name) {
        Pattern pattern = Pattern.compile(NAME_SURNAME_REGEX);
        return !StringUtils.isEmptyOrWhitespaceOnly(name)
                && pattern.matcher(name).matches();
    }

    @Override
    public boolean validateRegistration(RequestParameters params) {
        boolean isValid = true;

        String name = params.get(ParameterType.USER_NAME);
        String surname = params.get(ParameterType.USER_SURNAME);
        String login = params.get(ParameterType.USER_LOGIN);
        String password = params.get(ParameterType.USER_PASSWORD);
        String confirmPassword = params.get(ParameterType.USER_CONFIRM_PASSWORD);
        if (!validateName(name) ||
                !validateName(surname)) {
            isValid = false;
        }

        if (!validateLogin(login)) {
            isValid = false;
        }

        if (!validatePassword(password, confirmPassword)) {
            isValid = false;
        }

        return isValid;
    }
}
