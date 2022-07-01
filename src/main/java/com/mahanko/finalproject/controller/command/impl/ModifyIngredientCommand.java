package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.ValidationMessage;
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
import java.util.List;

import static com.mahanko.finalproject.controller.ParameterType.*;

public class ModifyIngredientCommand extends AsynchronousCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router router;
        try {
            RequestParameters parameters = new RequestParameters();
            String ingredientIdString = request.getParameter(INGREDIENT_ID);
            parameters.put(INGREDIENT_ID, ingredientIdString);
            parameters.put(INGREDIENT_NAME, request.getParameter(INGREDIENT_NAME));
            parameters.put(INGREDIENT_CALORIES, request.getParameter(INGREDIENT_CALORIES));
            parameters.put(INGREDIENT_PROTEINS, request.getParameter(INGREDIENT_PROTEINS));
            parameters.put(INGREDIENT_FATS, request.getParameter(INGREDIENT_FATS));
            parameters.put(INGREDIENT_CARBOHYDRATES, request.getParameter(INGREDIENT_CARBOHYDRATES));

            Part picturePart = request.getPart(INGREDIENT_PICTURE);
            if (picturePart.getSize() > 0) {
                String pictureString = CustomPictureEncoder.arrayToBase64(picturePart.getInputStream().readAllBytes());
                String pictureSize = String.valueOf(picturePart.getSize());
                String pictureName = picturePart.getSubmittedFileName();
                parameters.put(INGREDIENT_PICTURE, pictureString);
                parameters.put(INGREDIENT_PICTURE_SIZE, pictureSize);
                parameters.put(INGREDIENT_PICTURE_NAME, pictureName);
            }

            IngredientService service = IngredientServiceImpl.getInstance();
            boolean updated = service.update(Long.parseLong(ingredientIdString), parameters);
            List<String> validationMessages = parameters.getMultiple(ValidationMessage.VALIDATION_MESSAGES);
            router = validationMessages != null ?
                    fillResponse(response, updated, validationMessages) :
                    fillResponse(response, updated);
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException(e);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
            throw new CommandException(e);
        }

        return router;
    }
}
