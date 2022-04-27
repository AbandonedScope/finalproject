package com.mahanko.finalproject.validator;

public interface MenuItemValidator {
    boolean validateName(String name);
    boolean validateCost(double cost);
    boolean validateDescription(String description);
    boolean validatePicture(String pictureName, long size);
}
