package com.mahanko.finalproject.model.validator;

import java.util.List;

/**
 * The interface MenuItemValidator.
 */
public interface MenuItemValidator extends NameValidator {
    /**
     * Validate menu item cost.
     *
     * @param cost the menu item cost string representation.
     * @return {@code true}, if the menu item cost is valid.
     */
    boolean validateCost(String cost);

    /**
     * Validate menu item picture name and size.
     *
     * @param pictureName the picture name.
     * @param size        the picture size.
     * @return {@code true}, if the menu item picture name and size are valid.
     */
    boolean validatePicture(String pictureName, long size);

    /**
     * Validate menu item ingredient weights.
     *
     * @param weights the list with string representations of menu item ingredient weights.
     * @return {@code true}, if the menu item ingredient weights are valid.
     */
    boolean validateIngredientsWeights(List<String> weights);

    /**
     * Validate menu item ingredient weight.
     *
     * @param weight the string representations of menu item ingredient weight.
     * @return {@code true}, if the menu item ingredient weight is valid.
     */
    boolean validateWeight(String weight);
}
