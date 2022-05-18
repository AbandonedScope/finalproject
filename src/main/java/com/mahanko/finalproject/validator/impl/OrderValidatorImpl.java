package com.mahanko.finalproject.validator.impl;

import com.mahanko.finalproject.validator.OrderValidator;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
public class OrderValidatorImpl implements OrderValidator {
    private static final String DATETIME_FORMATTER_PATTERN = "HH:mm";

    @Override
    public boolean validateServingTime(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIME_FORMATTER_PATTERN);
        LocalDateTime dateTime = LocalDateTime.parse(time);
        return false;
    }
}
