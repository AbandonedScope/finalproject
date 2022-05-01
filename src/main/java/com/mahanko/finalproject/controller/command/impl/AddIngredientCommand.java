package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.service.IngredientService;
import com.mahanko.finalproject.model.service.impl.IngredientServiceImpl;
import com.mahanko.finalproject.util.CustomPictureEncoder;
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
            String pictureName = filePart.getSubmittedFileName();
            byte[] pictureBytes = filePart.getInputStream().readAllBytes();
            String ingredientPicture = CustomPictureEncoder.arrayToBase64(pictureBytes);
            long pictureSize = filePart.getSize();

            RequestParameters params = new RequestParameters();
            params.put(ParameterType.INGREDIENT_PICTURE, ingredientPicture);
            params.put(ParameterType.INGREDIENT_PICTURE_NAME, pictureName);
            params.put(ParameterType.INGREDIENT_PICTURE_SIZE, String.valueOf(pictureSize));
            params.put(ParameterType.INGREDIENT_NAME, request.getParameter(ParameterType.INGREDIENT_NAME));
            params.put(ParameterType.INGREDIENT_PROTEINS, request.getParameter(ParameterType.INGREDIENT_PROTEINS));
            params.put(ParameterType.INGREDIENT_FATS, request.getParameter(ParameterType.INGREDIENT_FATS));
            params.put(ParameterType.INGREDIENT_CARBOHYDRATES, request.getParameter(ParameterType.INGREDIENT_CARBOHYDRATES));
            params.put(ParameterType.INGREDIENT_CALORIES, request.getParameter(ParameterType.INGREDIENT_CALORIES));

            IngredientService ingredientService = new IngredientServiceImpl(new IngredientValidatorImpl());
            if (ingredientService.insert(params)) {
                request.setAttribute(ParameterType.INGREDIENT_ADD_FAILED_MESSAGE, INGREDIENT_SUCCESSFUL_ADDED_MESSAGE);
            } else {
                // FIXME: 01.05.2022 validation messages?
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
