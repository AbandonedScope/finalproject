package com.mahanko.finalproject.model.validator;

import java.math.BigDecimal;

public interface MenuItemValidator extends NameValidator {
    boolean validateCost(BigDecimal cost);

    boolean validatePicture(String pictureName, long size);
}
