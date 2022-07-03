package com.mahanko.finalproject.model.entity;

import java.util.Arrays;
import java.util.Locale;

/**
 * The enum PaymentType.
 */
public enum PaymentType {
    /**
     * The Cash.
     */
    CASH,
    /**
     * The Credit card.
     */
    CREDIT_CARD;

    /**
     * Define the PaymentType by its string representation.
     * @param paymentType the string representation of payment type
     * @return the PaymentType associated with given string or {@link PaymentType#CASH}.
     */
    public static PaymentType define(String paymentType) {
        if (Arrays.stream(PaymentType.values()).anyMatch( en -> en.toString().equals(paymentType))) {
            String type = paymentType.toUpperCase(Locale.ROOT).replace(' ', '_');
            return  PaymentType.valueOf(type);
        }

        return CASH;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT).replace('_', ' ');
    }
}
