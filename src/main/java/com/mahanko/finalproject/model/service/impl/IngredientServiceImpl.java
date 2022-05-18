package com.mahanko.finalproject.model.service.impl;

import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.dao.IngredientDao;
import com.mahanko.finalproject.model.dao.impl.IngredientDaoImpl;
import com.mahanko.finalproject.model.entity.menu.Ingredient;
import com.mahanko.finalproject.model.service.IngredientService;
import com.mahanko.finalproject.validator.IngredientValidator;
import com.mahanko.finalproject.validator.impl.IngredientValidatorImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mahanko.finalproject.controller.ParameterType.*;

public class IngredientServiceImpl implements IngredientService {

    @Override
    public Optional<Ingredient> findById(Long id) throws ServiceException {
        Ingredient ingredient;
        Optional<Ingredient> ingredientOptional = Optional.empty();
        try {
            ingredientOptional = IngredientDaoImpl.getInstance().findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return ingredientOptional;
    }

    @Override
    public List<Ingredient> findAll() throws ServiceException {
        List<Ingredient> ingredients;
        try {
            ingredients = IngredientDaoImpl.getInstance().findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return ingredients;
    }

    @Override
    public boolean insert(RequestParameters params) throws ServiceException {
        boolean isInserted = false;
        boolean isValid = true;
        String ingredientPicture = params.get(ParameterType.INGREDIENT_PICTURE);
        String pictureName = params.get(ParameterType.INGREDIENT_PICTURE_NAME);
        long pictureSize = Long.parseLong(params.get(ParameterType.INGREDIENT_PICTURE_SIZE));
        String ingredientName = params.get(ParameterType.INGREDIENT_NAME);
        // FIXME: 01.05.2022 number format
        double proteins = Double.parseDouble(params.get(ParameterType.INGREDIENT_PROTEINS));
        double fats = Double.parseDouble(params.get(ParameterType.INGREDIENT_FATS));
        double carbohydrates = Double.parseDouble(params.get(ParameterType.INGREDIENT_CARBOHYDRATES));
        double calories = Double.parseDouble(params.get(ParameterType.INGREDIENT_CALORIES));
        // FIXME: 01.05.2022 validation messages?
        IngredientValidator validator = new IngredientValidatorImpl();
        List<String> validationMessages = new ArrayList<>();
        if (!validator.validateName(ingredientName)) {
            isValid = false;
            validationMessages.add(INGREDIENT_NAME_VALIDATION_MESSAGE);
        }

        if (!validator.validatePicture(pictureName, pictureSize)) {
            isValid = false;
            validationMessages.add(INGREDIENT_PICTURE_VALIDATION_MESSAGE);
        }

        if (!validator.validateNumericField(proteins)) {
            isValid = false;
            validationMessages.add(INGREDIENT_PROTEINS_VALIDATION_MESSAGE);
        }

        if (!validator.validateNumericField(fats)) {
            isValid = false;
            validationMessages.add(INGREDIENT_FATS_VALIDATION_MESSAGE);
        }

        if (!validator.validateNumericField(carbohydrates)) {
            isValid = false;
            validationMessages.add(INGREDIENT_CARBOHYDRATES_VALIDATION_MESSAGE);
        }

        if (isValid) {
            try {
                Ingredient ingredient = Ingredient.newBuilder()
                        .setName(ingredientName)
                        .setCalories(calories)
                        .setProteins(proteins)
                        .setFats(fats)
                        .setCarbohydrates(carbohydrates)
                        .setPicture(ingredientPicture)
                        .build();

                IngredientDao dao = IngredientDaoImpl.getInstance();
                if (!dao.existWithName(ingredient.getName())) {
                    isInserted = dao.insert(ingredient);
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            params.put(ParameterType.VALIDATION_MESSAGES, validationMessages);
        }

        return isInserted;
    }
}
