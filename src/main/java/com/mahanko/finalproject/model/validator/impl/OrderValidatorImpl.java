package com.mahanko.finalproject.model.validator.impl;

import com.mahanko.finalproject.model.validator.OrderValidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class OrderValidatorImpl implements OrderValidator {
    private static final int MINIMAL_DELAY_TIME_IN_MINUTES = 29;
    private static final int MAXIMAL_DELAY_TIME_IN_DAYS = 1;
    private static final int MAXIMAL_ITEMS_AMOUNT = 20;

    @Override
    public boolean validateServingTime(String time) {
        boolean isValid = true;
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(time);
            LocalDateTime now = LocalDateTime.now();
            if (dateTime.isBefore(now.plus(MINIMAL_DELAY_TIME_IN_MINUTES, ChronoUnit.MINUTES))
            || dateTime.isAfter(now.plus(MAXIMAL_DELAY_TIME_IN_DAYS, ChronoUnit.DAYS))) {
                isValid = false;
            }
        } catch (DateTimeParseException e) {
            isValid = false;
        }

        return isValid;
    }

    @Override
    public boolean validateItemAmount(int amount) {
        return amount > 0 && amount <= MAXIMAL_ITEMS_AMOUNT;
    }
}
