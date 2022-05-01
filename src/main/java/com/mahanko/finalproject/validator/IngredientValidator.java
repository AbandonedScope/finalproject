package com.mahanko.finalproject.validator;

public interface IngredientValidator {
    boolean validateName(String name);
    boolean validateNumericField(double value);
    boolean validatePicture(String pictureName, long size);
    boolean validatePictureExtension(String pictureName);
    boolean validatePictureSize(long size);
}
