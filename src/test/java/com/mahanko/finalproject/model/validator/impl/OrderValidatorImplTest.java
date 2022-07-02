package com.mahanko.finalproject.model.validator.impl;

import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.testng.Assert.*;

public class OrderValidatorImplTest {
    private final OrderValidatorImpl orderValidator = new OrderValidatorImpl();

    @Test
    public void testValidateCorrectServingTime() {
        LocalDateTime correctDate = LocalDateTime.now().plus(50, ChronoUnit.MINUTES);
        assertTrue(orderValidator.validateServingTime(correctDate.toString()));
    }

    @Test
    public void testValidateInCorrectServingTimeNotDateTime() {
        String notDateTime = "blablabal";
        assertFalse(orderValidator.validateServingTime(notDateTime));
    }

    @Test
    public void testValidateInCorrectServingTimeAfter() {
        LocalDateTime incorrectDate = LocalDateTime.now().plus(2, ChronoUnit.DAYS);
        assertFalse(orderValidator.validateServingTime(incorrectDate.toString()));
    }

    @Test
    public void testValidateInCorrectServingTimeBefore() {
        LocalDateTime incorrectDate = LocalDateTime.now();
        assertFalse(orderValidator.validateServingTime(incorrectDate.toString()));
    }

    @Test
    public void testValidateCorrectItemAmount() {
        int correctItemAmount = 5;
        assertTrue(orderValidator.validateItemAmount(correctItemAmount));
    }

    @Test
    public void testValidateInCorrectItemAmount() {
        int incorrectItemAmount = -5;
        assertFalse(orderValidator.validateItemAmount(incorrectItemAmount));
    }
}