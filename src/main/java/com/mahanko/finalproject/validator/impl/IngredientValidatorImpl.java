package com.mahanko.finalproject.validator.impl;

import com.mahanko.finalproject.validator.IngredientValidator;
import com.mysql.cj.util.StringUtils;

import java.util.regex.Pattern;

public class IngredientValidatorImpl implements IngredientValidator {
    private static final String INGREDIENT_NAME_RUS_LONGER_2_SHORTER_45 = "[А-Яа-я\\s]{2,45}";
    private static final String INGREDIENT_NAME_ENG_LONGER_2_SHORTER_45 = "[A-Za-z\\s]{2,45}";
    private static final Double MINIMAL_NUMERIC_FIELD_VALUE = 0d;
    private static final Double MAXIMAL_NUMERIC_FIELD_VALUE = 5000d;
    private static final String PICTURE_PNG_EXTENSION = ".png";
    private static final int MAXIMAL_INGREDIENT_PICTURE_SIZE = 512 * 512;

    @Override
    public boolean validateName(String name) {
        return !StringUtils.isEmptyOrWhitespaceOnly(name)
                && Pattern.matches(INGREDIENT_NAME_RUS_LONGER_2_SHORTER_45, name)
                || Pattern.matches(INGREDIENT_NAME_ENG_LONGER_2_SHORTER_45, name);
    }

    @Override
    public boolean validateNumericField(double value) {
        return value >= MINIMAL_NUMERIC_FIELD_VALUE && value <= MAXIMAL_NUMERIC_FIELD_VALUE;
    }

    @Override
    public boolean validatePicture(String pictureName, long size) {
        return !StringUtils.isEmptyOrWhitespaceOnly(pictureName)
                && StringUtils.endsWithIgnoreCase(pictureName, PICTURE_PNG_EXTENSION)
                && size <= MAXIMAL_INGREDIENT_PICTURE_SIZE;
    }

    @Override
    public boolean validatePictureExtension(String pictureName) {
        return !StringUtils.isEmptyOrWhitespaceOnly(pictureName)
                && StringUtils.endsWithIgnoreCase(pictureName, PICTURE_PNG_EXTENSION);
    }

    @Override
    public boolean validatePictureSize(long size) {
        return size <= MAXIMAL_INGREDIENT_PICTURE_SIZE;
    }
}
