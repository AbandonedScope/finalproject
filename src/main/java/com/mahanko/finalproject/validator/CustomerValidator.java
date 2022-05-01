package com.mahanko.finalproject.validator;

import com.mahanko.finalproject.controller.RequestParameters;


public interface CustomerValidator {
    boolean validateLogin(String login);
    boolean validatePassword(String password, String confirmPassword);
    boolean validateName(String name);
    boolean validateRegistration(RequestParameters values);
}
