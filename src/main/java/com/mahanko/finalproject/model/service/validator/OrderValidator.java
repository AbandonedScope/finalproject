package com.mahanko.finalproject.model.service.validator;

public interface OrderValidator {
    boolean validateServingTime(String time);
    boolean validateItemAmount(int amount);
}
