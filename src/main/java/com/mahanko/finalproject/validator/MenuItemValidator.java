package com.mahanko.finalproject.validator;

public interface MenuItemValidator extends NameValidator {
    boolean validateCost(double cost);

    boolean validateDescription(String description);

    boolean validatePicture(String pictureName, long size);
}
