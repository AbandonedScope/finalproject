package com.mahanko.finalproject.model.validator;

import com.mahanko.finalproject.controller.RequestParameters;

/**
 * The interface CustomerValidator.
 */
public interface CustomerValidator extends NameValidator {
    /**
     * Validate the customer login.
     *
     * @param login the customer login.
     * @return {@code true}, if the customer login is valid.
     */
    boolean validateLogin(String login);

    /**
     * Validate the customer password.
     *
     * @param password        the customer password.
     * @param confirmPassword the customer confirm password.
     * @return {@code true}, if the customer password is valid.
     */
    boolean validatePassword(String password, String confirmPassword);

    /**
     * Validate the registration information.
     *
     * @param values the parameters, must contain customer name, surname, login, password, confirm password.
     * @return {@code true}, if the customer information is valid.
     */
    boolean validateRegistration(RequestParameters values);
}
