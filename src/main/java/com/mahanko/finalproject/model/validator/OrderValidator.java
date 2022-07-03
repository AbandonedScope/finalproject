package com.mahanko.finalproject.model.validator;

/**
 * The interface OrderValidator.
 */
public interface OrderValidator {
    /**
     * Validate order serving date and time.
     *
     * @param time the serving date and time.
     * @return {@code true}, if the order serving date and time are valid.
     */
    boolean validateServingTime(String time);

    /**
     * Validate order menu item count.
     *
     * @param amount the menu item count.
     * @return {@code true}, if the order menu item count is valid.
     */
    boolean validateItemAmount(int amount);
}
