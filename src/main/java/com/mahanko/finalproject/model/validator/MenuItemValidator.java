package com.mahanko.finalproject.model.validator;

import java.math.BigDecimal;
import java.util.List;

public interface MenuItemValidator extends NameValidator {
    boolean validateCost(BigDecimal cost);

    boolean validatePicture(String pictureName, long size);

    boolean validateIngredientsWeights(List<Double> weights);
}
