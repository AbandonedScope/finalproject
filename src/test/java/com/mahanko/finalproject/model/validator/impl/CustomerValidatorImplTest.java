package com.mahanko.finalproject.model.validator.impl;

import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.RequestParameters;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CustomerValidatorImplTest {
    private final CustomerValidatorImpl customerValidator = new CustomerValidatorImpl();


    @Test
    public void testValidateCorrectLogin() {
        String correctLogin = "maxanko_mv_20";
        assertTrue(customerValidator.validateLogin(correctLogin));
    }

    @Test
    public void testValidateInCorrectLogin() {
        String incorrectLogin = "*maxanko_mv_20*";
        assertFalse(customerValidator.validateLogin(incorrectLogin));
    }

    @Test
    public void testValidateCorrectSamePassword() {
        String correctPassword = "mM5*mM5*";
        assertTrue(customerValidator.validatePassword(correctPassword, correctPassword));
    }

    @Test
    public void testValidateCorrectDifferentPassword() {
        String correctPassword = "mM5*mM5*";
        assertFalse(customerValidator.validatePassword(correctPassword, correctPassword + "15"));
    }

    @Test
    public void testValidateInCorrectPassword() {
        String incorrectPassword = "nbhkihfy";
        assertFalse(customerValidator.validatePassword(incorrectPassword, incorrectPassword));
    }

    @Test
    public void testValidateCorrectName() {
        String correctName = "МаксимMahanko";
        assertTrue(customerValidator.validateName(correctName));
    }

    @Test
    public void testValidateInCorrectName() {
        String incorrectName = "15Максим";
        assertFalse(customerValidator.validateName(incorrectName));
    }

    @Test
    public void testValidateCorrectRegistration() {
        RequestParameters correctParameters = new RequestParameters();
        correctParameters.put(ParameterType.USER_NAME, "Максим");
        correctParameters.put(ParameterType.USER_SURNAME, "Маханько");
        correctParameters.put(ParameterType.USER_LOGIN, "maxanko_mv_20");
        correctParameters.put(ParameterType.USER_PASSWORD, "mM5*mM5*");
        correctParameters.put(ParameterType.USER_CONFIRM_PASSWORD, "mM5*mM5*");
        assertTrue(customerValidator.validateRegistration(correctParameters));
    }

    @Test
    public void testValidateInCorrectRegistration() {
        RequestParameters correctParameters = new RequestParameters();
        correctParameters.put(ParameterType.USER_NAME, "1Максим5");
        correctParameters.put(ParameterType.USER_SURNAME, "5Маханько1");
        correctParameters.put(ParameterType.USER_LOGIN, "*maxanko_mv_20*");
        correctParameters.put(ParameterType.USER_PASSWORD, "mM5mM5*");
        correctParameters.put(ParameterType.USER_CONFIRM_PASSWORD, "mM5*mM5*");
        assertFalse(customerValidator.validateRegistration(correctParameters));
    }
}