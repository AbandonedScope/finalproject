package com.mahanko.finalproject.model.validator;

/**
 * The interface IngredientValidator.
 */
public interface IngredientValidator extends NameValidator {
    /**
     * Validate ingredient numeric field.
     *
     * @param value the ingredient numeric field string representation. Example proteins, fats, calories and other.
     * @return {@code true}, if the ingredient numeric field is valid.
     */
    boolean validateNumericField(String value);

    /**
     * Validate ingredient picture name and size.
     *
     * @param pictureName the picture name.
     * @param size        the picture size.
     * @return {@code true}, if the ingredient picture name and size are valid.
     */
    boolean validatePicture(String pictureName, long size);

    /**
     * Validate ingredient picture name.
     *
     * @param pictureName the picture name.
     * @return {@code true}, if the ingredient picture is valid.
     */
    boolean validatePictureExtension(String pictureName);

    /**
     * Validate ingredient picture size.
     *
     * @param size the picture size.
     * @return {@code true}, if the ingredient picture size is valid.
     */
    boolean validatePictureSize(long size);
}
