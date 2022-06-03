package com.mahanko.finalproject.model.validator.impl;

import com.mahanko.finalproject.model.validator.MenuItemValidator;
import com.mysql.cj.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

public class MenuItemValidatorImpl implements MenuItemValidator {
    // FIXME: 03.06.2022 to property file
    private static final String MENU_ITEM_NAME_RUS_LONGER_2_SHORTER_45 = "[А-Яа-я\\s-]{2,45}";
    private static final String MENU_ITEM_NAME_ENG_LONGER_2_SHORTER_45 = "[A-Za-z\\s-]{2,45}";
    private static final BigDecimal MINIMAL_MENU_ITEM_COST = BigDecimal.valueOf(0.01d);
    private static final Double MINIMAL_INGREDIENT_WEIGHT = 0.01d;
    private static final Double MAXIMAL_INGREDIENT_WEIGHT = 1000.0d;
    private static final BigDecimal MAXIMAL_MENU_ITEM_COST = BigDecimal.valueOf(1000d);
    private static final String PICTURE_PNG_EXTENSION = ".png";
    private static final int MAXIMAL_MENU_ITEM_PICTURE_SIZE = 1080 * 1080 * 4;

    @Override
    public boolean validateName(String name) {
        return !StringUtils.isEmptyOrWhitespaceOnly(name)
                && (Pattern.matches(MENU_ITEM_NAME_RUS_LONGER_2_SHORTER_45, name)
                || Pattern.matches(MENU_ITEM_NAME_ENG_LONGER_2_SHORTER_45, name));
    }

    @Override
    public boolean validateCost(BigDecimal cost) {
        return cost.compareTo(MINIMAL_MENU_ITEM_COST) >= 0 && cost.compareTo(MAXIMAL_MENU_ITEM_COST) <= 0;
    }

    @Override
    public boolean validatePicture(String pictureName, long size) {
        return !StringUtils.isEmptyOrWhitespaceOnly(pictureName)
                && StringUtils.endsWithIgnoreCase(pictureName, PICTURE_PNG_EXTENSION)
                && size <= MAXIMAL_MENU_ITEM_PICTURE_SIZE;
    }

    @Override
    public boolean validateIngredientsWeights(List<Double> weights) {
        boolean isValid = true;
        if (weights.isEmpty()) {
            isValid = false;
        } else {
            for (Double weight : weights) {
                if (weight < MINIMAL_INGREDIENT_WEIGHT || weight > MAXIMAL_INGREDIENT_WEIGHT) {
                    isValid = false;
                    break;
                }
            }
        }

        return isValid;
    }
}
