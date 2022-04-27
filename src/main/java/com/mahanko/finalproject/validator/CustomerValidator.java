package com.mahanko.finalproject.validator;

import java.util.Map;

public interface CustomerValidator {
    boolean validateLogin(String login);
    boolean validatePassword(String password);
    boolean validateName(String name);
    boolean validateRegistration(Map<String, String> values);
}
