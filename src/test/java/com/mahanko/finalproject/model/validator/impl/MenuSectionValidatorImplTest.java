package com.mahanko.finalproject.model.validator.impl;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MenuSectionValidatorImplTest {
    private final MenuSectionValidatorImpl sectionValidator = new MenuSectionValidatorImpl();

    @Test
    public void testValidateCorrectNameRussian() {
        String correctName = "Супы";
        assertTrue(sectionValidator.validateName(correctName));
    }

    @Test
    public void testValidateCorrectNameEnglish() {
        String correctName = "Soups";
        assertTrue(sectionValidator.validateName(correctName));
    }

    @Test
    public void testValidateInCorrectName() {
        String incorrectName = "д";
        assertFalse(sectionValidator.validateName(incorrectName));
    }
}