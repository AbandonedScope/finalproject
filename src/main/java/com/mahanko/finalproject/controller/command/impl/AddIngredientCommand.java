package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.service.IngredientService;
import com.mahanko.finalproject.model.service.impl.IngredientServiceImpl;
import com.mahanko.finalproject.util.CustomPictureEncoder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.mahanko.finalproject.controller.ParameterType.*;

public class AddIngredientCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String INGREDIENT_ALREADY_EXISTS_MESSAGE = "Such ingredient already exits.";
    private static final String INGREDIENT_SUCCESSFUL_ADDED_MESSAGE = "Ingredient was successfully added.";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router route = new Router(PagePath.ADD_INGREDIENT, Router.Type.FORWARD);
        try {
            Part filePart = request.getPart(INGREDIENT_PICTURE);
            String pictureName = filePart.getSubmittedFileName();
            byte[] pictureBytes = filePart.getInputStream().readAllBytes();
            String ingredientPicture = CustomPictureEncoder.arrayToBase64(pictureBytes);
            long pictureSize = filePart.getSize();

            RequestParameters params = new RequestParameters();
            params.put(INGREDIENT_PICTURE, ingredientPicture);
            params.put(INGREDIENT_PICTURE_NAME, pictureName);
            params.put(INGREDIENT_PICTURE_SIZE, String.valueOf(pictureSize));
            params.put(INGREDIENT_NAME, request.getParameter(INGREDIENT_NAME));
            params.put(INGREDIENT_PROTEINS, request.getParameter(INGREDIENT_PROTEINS));
            params.put(INGREDIENT_FATS, request.getParameter(INGREDIENT_FATS));
            params.put(INGREDIENT_CARBOHYDRATES, request.getParameter(INGREDIENT_CARBOHYDRATES));
            params.put(INGREDIENT_CALORIES, request.getParameter(INGREDIENT_CALORIES));

            IngredientService ingredientService = IngredientServiceImpl.getInstance();
            if (ingredientService.insert(params)) {
                request.setAttribute(INGREDIENT_ADD_MESSAGE, INGREDIENT_SUCCESSFUL_ADDED_MESSAGE);
            } else {
                if (!params.fillRequestWithValidations(request)) {
                    // FIXME: 11.05.2022 
                    request.setAttribute(INGREDIENT_ALREADY_EXISTS_MESSAGE, INGREDIENT_ALREADY_EXISTS_MESSAGE);
                }
            }
        } catch (IOException | ServletException e) {
            logger.log(Level.ERROR, e);
            throw new CommandException(e);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return route;
    }
}
