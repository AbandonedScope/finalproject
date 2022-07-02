package com.mahanko.finalproject.model.validator.impl;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class MenuItemValidatorImplTest {
    private final MenuItemValidatorImpl menuItemValidator = new MenuItemValidatorImpl();


    @Test
    public void testValidateCorrectName() {
        String correctName = "Овощной салат";
        assertTrue(menuItemValidator.validateName(correctName));
    }

    @Test
    public void testValidateInCorrectName() {
        String incorrectName = "ч";
        assertFalse(menuItemValidator.validateName(incorrectName));
    }

    @Test
    public void testValidateCorrectCost() {
        String correctCost = "100";
        assertTrue(menuItemValidator.validateCost(correctCost));
    }

    @Test
    public void testValidateInCorrectCost() {
        String incorrectCost = "af";
        assertFalse(menuItemValidator.validateCost(incorrectCost));
    }

    @Test
    public void testValidateInCorrectCostOutOfBounds() {
        String incorrectCost = "0.001";
        assertFalse(menuItemValidator.validateCost(incorrectCost));
    }

    @Test
    public void testValidateCorrectPicture() {
        String correctPictureName = "meal.png";
        long correctSize = 800;
        assertTrue(menuItemValidator.validatePicture(correctPictureName, correctSize));
    }

    @Test
    public void testValidateInCorrectPictureName() {
        String incorrectPictureName = "meal";
        long correctSize = 1024;
        assertFalse(menuItemValidator.validatePicture(incorrectPictureName, correctSize));
    }

    @Test
    public void testValidateInCorrectPictureSize() {
        String correctPictureName = "meal.png";
        long incorrectSize = 1080 * 1080 * 4 + 1;
        assertFalse(menuItemValidator.validatePicture(correctPictureName, incorrectSize));
    }

    @Test
    public void testValidateCorrectWeight() {
        String correctWeight = "500";
        assertTrue(menuItemValidator.validateWeight(correctWeight));
    }

    @Test
    public void testValidateInCorrectWeight() {
        String incorrectWeight = "5000";
        assertFalse(menuItemValidator.validateWeight(incorrectWeight));
    }

    @Test
    public void testValidateInCorrectWeightNotNumber() {
        String notNumber = "sdgfdg";
        assertFalse(menuItemValidator.validateWeight(notNumber));
    }

    @Test
    public void testValidateCorrectWeights() {
        var notNumbers = List.of("500", "17", "125");
        assertTrue(menuItemValidator.validateIngredientsWeights(notNumbers));
    }

    @Test
    public void testValidateInCorrectWeightsNotNumbers() {
        var notNumbers = List.of("sdgfdg");
        assertFalse(menuItemValidator.validateIngredientsWeights(notNumbers));
    }

    @Test
    public void testValidateInCorrectWeightsEmptyList() {
        var notNumbers = new ArrayList<String>();
        assertFalse(menuItemValidator.validateIngredientsWeights(notNumbers));
    }

    @Test
    public void testValidateInCorrectWeightsNullList() {
        assertFalse(menuItemValidator.validateIngredientsWeights(null));
    }
}