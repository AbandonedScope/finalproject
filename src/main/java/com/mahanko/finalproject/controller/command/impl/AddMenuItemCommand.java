package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.ValidationMessage;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.service.MenuItemService;
import com.mahanko.finalproject.model.service.impl.MenuItemServiceImpl;
import com.mahanko.finalproject.util.CustomPictureEncoder;
import com.mahanko.finalproject.model.entity.menu.MenuItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;

import static com.mahanko.finalproject.controller.ParameterType.*;
import static com.mahanko.finalproject.controller.ValidationMessage.VALIDATION_MESSAGES;

/**
 * The {@link AsynchronousCommand} that insert new {@link MenuItem} into database.
 */
public class AddMenuItemCommand extends AsynchronousCommand {
    /**
     * Used for writing logs
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     *  Executes a command.
     * @param request  The request
     * @param response The responce
     * @return The router with type {@link Router.Type#NONE}
     * @throws CommandException the command exception
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router route;
        try {
            RequestParameters parameters = new RequestParameters();
            String[] ingredientIdsArray = request.getParameterValues(INGREDIENT_ID);
            String[] ingredientWeightsArray = request.getParameterValues(INGREDIENT_WEIGHT);
            List<String> ingredientIds;
            List<String> ingredientWeights;
            if (ingredientIdsArray != null) {
                ingredientIds = List.of(ingredientIdsArray);
                ingredientWeights = List.of(ingredientWeightsArray);
            } else {
                ingredientIds = List.of();
                ingredientWeights = List.of();
            }
            parameters.put(INGREDIENT_ID, ingredientIds);
            parameters.put(INGREDIENT_WEIGHT, ingredientWeights);

            Part filePart = request.getPart(MENU_ITEM_PICTURE);
            byte[] pictureBytes = filePart.getInputStream().readAllBytes();
            String menuItemPicture = CustomPictureEncoder.arrayToBase64(pictureBytes);
            parameters.put(MENU_ITEM_PICTURE, menuItemPicture);

            parameters.put(MENU_ITEM_PICTURE_NAME, filePart.getSubmittedFileName());
            parameters.put(MENU_ITEM_PICTURE_SIZE, String.valueOf(filePart.getSize()));
            parameters.put(MENU_ITEM_NAME, request.getParameter(MENU_ITEM_NAME));
            parameters.put(MENU_ITEM_COST, request.getParameter(MENU_ITEM_COST));
            parameters.put(MENU_ITEM_SECTION_ID, request.getParameter(MENU_ITEM_SECTION_ID));

            MenuItemService menuItemService = MenuItemServiceImpl.getInstance();
            boolean inserted = menuItemService.insertNew(parameters);
            if (inserted) {
                route = fillResponse(response, true);
            } else {
                List<String> validationMessages = parameters.getMultiple(VALIDATION_MESSAGES);
                if (validationMessages == null) {
                    validationMessages = List.of(ValidationMessage.MENU_ITEM_WITH_SUCH_NAME_ALREADY_EXISTS_MESSAGE);
                }

                route = fillResponse(response, false, validationMessages);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
            throw new CommandException(e);
        }

        return route;
    }
}
