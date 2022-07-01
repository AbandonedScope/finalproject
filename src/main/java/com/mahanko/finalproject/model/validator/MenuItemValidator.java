package com.mahanko.finalproject.model.validator;

import java.util.List;

public interface MenuItemValidator extends NameValidator {
    boolean validateCost(String cost);

    boolean validatePicture(String pictureName, long size);

    boolean validateIngredientsWeights(List<String> weights);

    boolean validateWeight(String weight);
}
