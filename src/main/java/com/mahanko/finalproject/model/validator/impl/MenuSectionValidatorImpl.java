package com.mahanko.finalproject.model.validator.impl;

import com.mahanko.finalproject.model.validator.MenuSectionValidator;
import com.mysql.cj.util.StringUtils;

import java.util.regex.Pattern;

public class MenuSectionValidatorImpl implements MenuSectionValidator {
    private static final String MENU_SECTION_NAME_RUS = "[А-Яа-я\\s]{2,45}";
    private static final String MENU_SECTION_NAME_ENG = "[A-Za-z\\s]{2,45}";

    @Override
    public boolean validateName(String name) {
        return !StringUtils.isEmptyOrWhitespaceOnly(name)
                && (Pattern.matches(MENU_SECTION_NAME_RUS, name)
                || Pattern.matches(MENU_SECTION_NAME_ENG, name));
    }
}
