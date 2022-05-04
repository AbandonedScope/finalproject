package com.mahanko.finalproject.validator;

public interface IngredientValidator extends NameValidator {
    boolean validateNumericField(double value);

    boolean validatePicture(String pictureName, long size);

    boolean validatePictureExtension(String pictureName);

    boolean validatePictureSize(long size);
}
