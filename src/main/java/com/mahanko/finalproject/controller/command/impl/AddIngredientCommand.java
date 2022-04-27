package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.IngredientComponent;
import com.mahanko.finalproject.model.service.IngredientService;
import com.mahanko.finalproject.model.service.impl.IngredientServiceImpl;
import com.mahanko.finalproject.util.CustomStringEncoder;
import com.mahanko.finalproject.validator.IngredientValidator;
import com.mahanko.finalproject.validator.impl.IngredientValidatorImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class AddIngredientCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String INGREDIENT_ALREADY_EXISTS_MESSAGE = "Such ingredient already exits.";
    private static final String INGREDIENT_SUCCESSFUL_ADDED_MESSAGE = "Ingredient was successfully added.";
    private static final String INGREDIENT_VALIDATING_SOME_FIELD_WRONG_MESSAGE = "Some of the ingredient fields are wrong, please check yourself.";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router rote = new Router(PagePath.ADD_INGREDIENT, Router.Type.FORWARD);
        try {
            Part filePart = request.getPart(ParameterType.INGREDIENT_PICTURE);
            IngredientValidator ingredientValidator = new IngredientValidatorImpl();

            String pictureName = filePart.getSubmittedFileName();
            long pictureSize = filePart.getSize();
            String name = request.getParameter(ParameterType.INGREDIENT_NAME);
            double proteins = Double.parseDouble(request.getParameter(ParameterType.INGREDIENT_PROTEINS));
            double fats = Double.parseDouble(request.getParameter(ParameterType.INGREDIENT_FATS));
            double carbohydrates = Double.parseDouble(request.getParameter(ParameterType.INGREDIENT_CARBOHYDRATES));
            double calories = Double.parseDouble(request.getParameter(ParameterType.INGREDIENT_CALORIES));

            if (ingredientValidator.validateName(name)
                    && ingredientValidator.validateNumericField(proteins)
                    && ingredientValidator.validateNumericField(fats)
                    && ingredientValidator.validateNumericField(carbohydrates)
                    && ingredientValidator.validateNumericField(calories)
                    && ingredientValidator.validatePicture(pictureName, pictureSize)) {
                String ingredientPicture = CustomStringEncoder.arrayToBase64(filePart.getInputStream().readAllBytes());

                IngredientComponent ingredient = IngredientComponent.newBuilder()
                        .setName(name)
                        .setProteins(proteins)
                        .setFats(fats)
                        .setCarbohydrates(carbohydrates)
                        .setCalories(calories)
                        .setPicture(ingredientPicture)
                        .build();

                IngredientService ingredientService = new IngredientServiceImpl();
                if (ingredientService.insert(ingredient)) {
                    request.setAttribute(ParameterType.INGREDIENT_ADD_FAILED_MESSAGE, INGREDIENT_SUCCESSFUL_ADDED_MESSAGE);
                } else {
                    request.setAttribute(ParameterType.INGREDIENT_ADD_FAILED_MESSAGE, INGREDIENT_ALREADY_EXISTS_MESSAGE);
                }
            } else {
                request.setAttribute(ParameterType.INGREDIENT_ADD_FAILED_MESSAGE, INGREDIENT_VALIDATING_SOME_FIELD_WRONG_MESSAGE);
            }
            // FIXME: 27.04.2022 NullPointer, NUmberFormat?
        } catch (IOException | ServletException e) {
            logger.log(Level.ERROR, e);
            throw new CommandException(e);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return rote;
    }
}
