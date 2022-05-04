package com.mahanko.finalproject.validator;

import com.mahanko.finalproject.controller.RequestParameters;


public interface CustomerValidator extends NameValidator {
    boolean validateLogin(String login);

    boolean validatePassword(String password, String confirmPassword);

    boolean validateRegistration(RequestParameters values);
}
