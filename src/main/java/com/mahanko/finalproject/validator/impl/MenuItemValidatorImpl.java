package com.mahanko.finalproject.validator.impl;

import com.mahanko.finalproject.validator.MenuItemValidator;
import com.mysql.cj.util.StringUtils;

import java.util.regex.Pattern;

public class MenuItemValidatorImpl implements MenuItemValidator {
    private static final String MENU_ITEM_NAME_RUS_LONGER_2_SHORTER_45 = "[А-Яа-я\\s]{2,45}";
    private static final String MENU_ITEM_NAME_ENG_LONGER_2_SHORTER_45 = "[A-Za-z\\s]{2,45}";
    private static final String MENU_ITEM_DESCRIPTION_LONGER_THEN_3 = "[\\w\\p{Punct}\\s]{3,}";
    private static final double MINIMAL_MENU_ITEM_COST = 0.01d;
    private static final double MAXIMAL_MENU_ITEM_COST = 1000d;
    private static final String PICTURE_PNG_EXTENSION = ".png";
    private static final int MAXIMAL_MENU_ITEM_PICTURE_SIZE = 1024 * 1024 * 4;

    @Override
    public boolean validateName(String name) {
        return !StringUtils.isEmptyOrWhitespaceOnly(name)
                && Pattern.matches(MENU_ITEM_NAME_RUS_LONGER_2_SHORTER_45, name)
                || Pattern.matches(MENU_ITEM_NAME_ENG_LONGER_2_SHORTER_45, name);
    }

    @Override
    public boolean validateCost(double cost) {
        return cost >= MINIMAL_MENU_ITEM_COST && cost <= MAXIMAL_MENU_ITEM_COST;
    }

    @Override
    public boolean validateDescription(String description) {
        return !StringUtils.isEmptyOrWhitespaceOnly(description)
                && Pattern.matches(MENU_ITEM_DESCRIPTION_LONGER_THEN_3, description);
    }

    @Override
    public boolean validatePicture(String pictureName, long size) {
        return !StringUtils.isEmptyOrWhitespaceOnly(pictureName)
                && StringUtils.endsWithIgnoreCase(pictureName, PICTURE_PNG_EXTENSION)
                && size <= MAXIMAL_MENU_ITEM_PICTURE_SIZE;
    }
}
