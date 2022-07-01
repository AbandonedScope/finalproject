package com.mahanko.finalproject.model.service.impl;

import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.dao.IngredientDao;
import com.mahanko.finalproject.model.dao.impl.IngredientDaoImpl;
import com.mahanko.finalproject.model.entity.menu.Ingredient;
import com.mahanko.finalproject.model.service.IngredientService;
import com.mahanko.finalproject.model.validator.IngredientValidator;
import com.mahanko.finalproject.model.validator.impl.IngredientValidatorImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.mahanko.finalproject.controller.ParameterType.*;
import static com.mahanko.finalproject.controller.ValidationMessage.*;

public class IngredientServiceImpl implements IngredientService {
    private static final Logger logger = LogManager.getLogger();
    private static final  IngredientServiceImpl instance = new IngredientServiceImpl();

    private IngredientServiceImpl() {
    }

    public static IngredientServiceImpl getInstance() {
        return instance;
    }

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
        IngredientDao ingredientDao = IngredientDaoImpl.getInstance();
        try {
            ingredients = ingredientDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return ingredients;
    }

    @Override
    public List<Ingredient> findByName(String name) throws ServiceException {
        List<Ingredient> ingredients;
        IngredientDao ingredientDao = IngredientDaoImpl.getInstance();
        try {
            ingredients = ingredientDao.findByName(name);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return ingredients;
    }

    @Override
    public Optional<Ingredient> insert(RequestParameters params) throws ServiceException {
        Optional<Ingredient> optionalIngredient = Optional.empty();
        boolean isValid = true;
        String ingredientPicture = params.get(ParameterType.INGREDIENT_PICTURE);
        String pictureName = params.get(ParameterType.INGREDIENT_PICTURE_NAME);
        long pictureSize = Long.parseLong(params.get(ParameterType.INGREDIENT_PICTURE_SIZE));
        String ingredientName = params.get(ParameterType.INGREDIENT_NAME).trim();
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
                if (!dao.existsWithName(ingredient.getName()) && dao.insert(ingredient)) {
                    optionalIngredient = Optional.of(ingredient);
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            params.put(VALIDATION_MESSAGES, validationMessages);
        }

        return optionalIngredient;
    }

    @Override
    public boolean remove(long id) throws ServiceException {
        boolean removed = false;
        try {
            IngredientDao ingredientDao = IngredientDaoImpl.getInstance();
            if (ingredientDao.existsMerge(id)) {
                removed = ingredientDao.setHidden(id, true);
            } else {
                removed = ingredientDao.remove(id);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return removed;
    }

    @Override
    public boolean update(long id, RequestParameters parameters) throws ServiceException {
        boolean isValid = true;
        boolean updated = false;
        String ingredientIdString = parameters.get(INGREDIENT_ID);
        String ingredientName = parameters.get(INGREDIENT_NAME);
        String ingredientCaloriesString = parameters.get(INGREDIENT_CALORIES);
        String ingredientProteinsString = parameters.get(INGREDIENT_PROTEINS);
        String ingredientFatsString = parameters.get(INGREDIENT_FATS);
        String ingredientCarbohydratesString = parameters.get(INGREDIENT_CARBOHYDRATES);
        String ingredientPicture = parameters.get(ParameterType.INGREDIENT_PICTURE);

        try {
            long ingredientId = Long.parseLong(ingredientIdString);
            double ingredientCalories = Double.parseDouble(ingredientCaloriesString);
            double ingredientProteins = Double.parseDouble(ingredientProteinsString);
            double ingredientFats = Double.parseDouble(ingredientFatsString);
            double ingredientCarbohydrates = Double.parseDouble(ingredientCarbohydratesString);

            IngredientValidator validator = new IngredientValidatorImpl();
            List<String> validationMessages = new ArrayList<>();
            if (!validator.validateName(ingredientName)) {
                isValid = false;
                validationMessages.add(INGREDIENT_NAME_VALIDATION_MESSAGE);
            }

            if (!validator.validateNumericField(ingredientCalories)) {
                isValid = false;
                validationMessages.add(INGREDIENT_CALORIES_VALIDATION_MESSAGE);
            }

            if (!validator.validateNumericField(ingredientProteins)) {
                isValid = false;
                validationMessages.add(INGREDIENT_PROTEINS_VALIDATION_MESSAGE);
            }

            if (!validator.validateNumericField(ingredientFats)) {
                isValid = false;
                validationMessages.add(INGREDIENT_FATS_VALIDATION_MESSAGE);
            }

            if (!validator.validateNumericField(ingredientCarbohydrates)) {
                isValid = false;
                validationMessages.add(INGREDIENT_CARBOHYDRATES_VALIDATION_MESSAGE);
            }

            if (ingredientPicture != null) {
                String pictureName = parameters.get(ParameterType.INGREDIENT_PICTURE_NAME);
                long pictureSize = Long.parseLong(parameters.get(ParameterType.INGREDIENT_PICTURE_SIZE));
                if (!validator.validatePicture(pictureName, pictureSize)) {
                    isValid = false;
                    validationMessages.add(INGREDIENT_PICTURE_VALIDATION_MESSAGE);
                }
            }

            if (isValid) {
                Ingredient ingredient = Ingredient.newBuilder()
                        .setId(ingredientId)
                        .setName(ingredientName)
                        .setCalories(ingredientCalories)
                        .setProteins(ingredientProteins)
                        .setFats(ingredientFats)
                        .setCarbohydrates(ingredientCarbohydrates)
                        .setPicture(ingredientPicture)
                        .build();
                IngredientDao ingredientDao = IngredientDaoImpl.getInstance();
                if (ingredientPicture == null) {
                    Ingredient oldIngredient = ingredientDao.findById(id).orElseThrow();
                    ingredient.setPicture(oldIngredient.getPictureBase64());
                }

                updated = ingredientDao.update(id, ingredient);
            } else {
                parameters.put(VALIDATION_MESSAGES, validationMessages);
            }
        } catch (NumberFormatException | NoSuchElementException e) {
            logger.log(Level.ERROR, e);
            throw new ServiceException(e);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return updated;
    }
}
