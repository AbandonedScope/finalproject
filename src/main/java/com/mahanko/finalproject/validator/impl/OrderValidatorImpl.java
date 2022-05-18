package com.mahanko.finalproject.validator.impl;

import com.mahanko.finalproject.validator.OrderValidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class OrderValidatorImpl implements OrderValidator {

    @Override
    public boolean validateServingTime(String time) {
        boolean isValid = true;
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(time);
            LocalDateTime now = LocalDateTime.now();
            if (dateTime.isBefore(now.plus(29, ChronoUnit.MINUTES))
            || dateTime.isAfter(now.plus(1, ChronoUnit.DAYS))) {
                isValid = false;
            }
        } catch (DateTimeParseException e) {
            isValid = false;
        }

        return isValid;
    }
}
