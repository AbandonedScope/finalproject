package com.mahanko.finalproject.model.validator.impl;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class IngredientValidatorImplTest {
    private final IngredientValidatorImpl ingredientValidator = new IngredientValidatorImpl();

    @Test
    public void testValidateCorrectName() {
        String correctName = "Сельдерей";
        assertTrue(ingredientValidator.validateName(correctName));
    }

    @Test
    public void testValidateInCorrectName() {
        String incorrectName = "ь";
        assertFalse(ingredientValidator.validateName(incorrectName));
    }

    @Test
    public void testValidateCorrectNumericField() {
        String correctField = "59";
        assertTrue(ingredientValidator.validateNumericField(correctField));
    }

    @Test
    public void testValidateInCorrectNumericField() {
        String incorrectField = "af";
        assertFalse(ingredientValidator.validateNumericField(incorrectField));
    }

    @Test
    public void testValidateInCorrectNumericFieldOutOfBounds() {
        String incorrectField = "1000";
        assertFalse(ingredientValidator.validateNumericField(incorrectField));
    }

    @Test
    public void testValidateCorrectPicture() {
        String correctPictureName = "picture.png";
        long correctSize = 800;
        assertTrue(ingredientValidator.validatePicture(correctPictureName, correctSize));
    }

    @Test
    public void testValidateInCorrectPictureName() {
        String incorrectPictureName = "picture.pn";
        long correctSize = 800;
        assertFalse(ingredientValidator.validatePicture(incorrectPictureName, correctSize));
    }

    @Test
    public void testValidateInCorrectPictureSize() {
        String correctPictureName = "picture.png";
        long incorrectSize = 1024 * 1024 + 1;
        assertFalse(ingredientValidator.validatePicture(correctPictureName, incorrectSize));
    }
}