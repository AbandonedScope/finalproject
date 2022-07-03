package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.ValidationMessage;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.Ingredient;
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
import java.util.List;
import java.util.Optional;

import static com.mahanko.finalproject.controller.ParameterType.*;
import static com.mahanko.finalproject.controller.ValidationMessage.VALIDATION_MESSAGES;

/**
 * The {@link AsynchronousCommand} that insert new {@link Ingredient} into database.
 */
public class AddIngredientCommand extends AsynchronousCommand {
    /**
     * Used for writing logs
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     *  Executes a command.
     * @param request  The request
     * @param response The response
     * @return The router with type {@link Router.Type#NONE}
     * @throws CommandException the command exception
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router route;
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
            Optional<Ingredient> ingredientOptional = ingredientService.insert(params);
            if (ingredientOptional.isPresent()) {
                route = fillResponse(response, true);
            } else {
                List<String> validationMessages = params.getMultiple(VALIDATION_MESSAGES);
                if (validationMessages == null) {
                    validationMessages = List.of(ValidationMessage.INGREDIENT_WITH_SUCH_NAME_ALREADY_EXISTS_MESSAGE);
                }

                route = fillResponse(response, false, validationMessages);
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
