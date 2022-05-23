package com.mahanko.finalproject.model.entity;

import java.util.Arrays;
import java.util.Locale;

public enum PaymentType {
    CASH,
    CREDIT_CARD;

    public static PaymentType define(String paymentType) {
        if (Arrays.stream(PaymentType.values()).anyMatch( en -> en.toString().equals(paymentType))) {
            String type = paymentType.toUpperCase(Locale.ROOT).replace(' ', '_');
            return  PaymentType.valueOf(type);
        }

        // FIXME: 23.05.2022
        return null;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT).replace('_', ' ');
    }
}
