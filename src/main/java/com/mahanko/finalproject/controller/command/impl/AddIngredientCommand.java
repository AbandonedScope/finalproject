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

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router rote = new Router(PagePath.ADD_INGREDIENT, Router.Type.FORWARD);
        try {
            Part filePart = request.getPart(ParameterType.INGREDIENT_PICTURE);
            String ingredientPicture = CustomStringEncoder.arrayToBase64(filePart.getInputStream().readAllBytes());
            IngredientComponent ingredient = IngredientComponent.newBuilder()
                    .setName(request.getParameter(ParameterType.INGREDIENT_NAME))
                    .setProteins(Double.parseDouble(request.getParameter(ParameterType.INGREDIENT_PROTEINS)))
                    .setFats(Double.parseDouble(request.getParameter(ParameterType.INGREDIENT_FATS)))
                    .setCarbohydrates(Double.parseDouble(request.getParameter(ParameterType.INGREDIENT_CARBOHYDRATES)))
                    .setCalories(Double.parseDouble(request.getParameter(ParameterType.INGREDIENT_CALORIES)))
                    .setPicture(ingredientPicture)
                    .build();
            IngredientService ingredientService = new IngredientServiceImpl();
            if (ingredientService.insert(ingredient)) {
                request.setAttribute(ParameterType.INGREDIENT_ADD_FAILED_MESSAGE, INGREDIENT_SUCCESSFUL_ADDED_MESSAGE);
            } else {
                request.setAttribute(ParameterType.INGREDIENT_ADD_FAILED_MESSAGE, INGREDIENT_ALREADY_EXISTS_MESSAGE);
            }
        } catch (IOException | ServletException e) {
            logger.log(Level.ERROR, e);
            throw new CommandException(e);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return rote;
    }
}
