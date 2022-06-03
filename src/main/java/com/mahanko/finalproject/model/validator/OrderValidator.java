package com.mahanko.finalproject.model.validator;

public interface OrderValidator {
    boolean validateServingTime(String time);

    boolean validateItemAmount(int amount);
}
