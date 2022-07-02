package com.mahanko.finalproject.model.validator.impl;

import com.mahanko.finalproject.model.validator.IngredientValidator;
import com.mysql.cj.util.StringUtils;

import java.util.regex.Pattern;

public class IngredientValidatorImpl implements IngredientValidator {
    private static final String INGREDIENT_NAME_RUS_LONGER_2_SHORTER_45 = "[А-Яа-я\\s]{2,45}";
    private static final String INGREDIENT_NAME_ENG_LONGER_2_SHORTER_45 = "[A-Za-z\\s]{2,45}";
    private static final Double MINIMAL_NUMERIC_FIELD_VALUE = 0d;
    private static final Double MAXIMAL_NUMERIC_FIELD_VALUE = 999d;
    private static final String PICTURE_PNG_EXTENSION = ".png";
    private static final int MAXIMAL_INGREDIENT_PICTURE_SIZE = 1024 * 1024;

    @Override
    public boolean validateName(String name) {
        return !StringUtils.isEmptyOrWhitespaceOnly(name)
                && (Pattern.matches(INGREDIENT_NAME_RUS_LONGER_2_SHORTER_45, name)
                || Pattern.matches(INGREDIENT_NAME_ENG_LONGER_2_SHORTER_45, name));
    }

    @Override
    public boolean validateNumericField(String value) {
        boolean result;
        try {
            double val = Double.parseDouble(value);
            result = val >= MINIMAL_NUMERIC_FIELD_VALUE && val <= MAXIMAL_NUMERIC_FIELD_VALUE;
        } catch (NumberFormatException e) {
            result = false;
        }

        return result;
    }

    @Override
    public boolean validatePicture(String pictureName, long size) {
        return validatePictureExtension(pictureName)
                && validatePictureSize(size);
    }

    @Override
    public boolean validatePictureExtension(String pictureName) {
        return !StringUtils.isEmptyOrWhitespaceOnly(pictureName)
                && StringUtils.endsWithIgnoreCase(pictureName, PICTURE_PNG_EXTENSION);
    }

    @Override
    public boolean validatePictureSize(long size) {
        return size <= MAXIMAL_INGREDIENT_PICTURE_SIZE && size >= 0;
    }
}
