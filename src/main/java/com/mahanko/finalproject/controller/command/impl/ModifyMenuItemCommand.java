package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.service.MenuItemService;
import com.mahanko.finalproject.model.service.impl.MenuItemServiceImpl;
import com.mahanko.finalproject.util.CustomPictureEncoder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.mahanko.finalproject.controller.ParameterType.*;

public class ModifyMenuItemCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router router = new Router(PagePath.MODIFY_MENU_ITEM, Router.Type.FORWARD);
        try {
            RequestParameters parameters = new RequestParameters();

            Part filePart = request.getPart(MENU_ITEM_PICTURE);
            if (filePart.getSize() > 0) {
                byte[] pictureBytes = filePart.getInputStream().readAllBytes();
                String menuItemPicture = CustomPictureEncoder.arrayToBase64(pictureBytes);
                parameters.put(MENU_ITEM_PICTURE, menuItemPicture);
                parameters.put(MENU_ITEM_PICTURE_NAME, filePart.getSubmittedFileName());
                parameters.put(MENU_ITEM_PICTURE_SIZE, String.valueOf(filePart.getSize()));
            }

            List<String> ingredientIds = Arrays.stream(request.getParameterValues(INGREDIENT_ID)).collect(Collectors.toList());
            List<String> ingredientWeights = Arrays.stream(request.getParameterValues(INGREDIENT_WEIGHT)).collect(Collectors.toList());
            parameters.put(INGREDIENT_ID, ingredientIds);
            parameters.put(INGREDIENT_WEIGHT, ingredientWeights);

            parameters.put(MENU_ITEM_NAME, request.getParameter(MENU_ITEM_NAME));
            parameters.put(MENU_ITEM_COST, request.getParameter(MENU_ITEM_COST));
            parameters.put(MENU_ITEM_SECTION_ID, request.getParameter(MENU_ITEM_SECTION_ID));
            String menuItemId = request.getParameter(MENU_ITEM_ID);
            parameters.put(MENU_ITEM_ID, menuItemId);
            MenuItemService menuItemService = MenuItemServiceImpl.getInstance();
            menuItemService.update(Integer.parseInt(menuItemId), parameters);
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException(e);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
            throw new CommandException(e);
        }

        return router;
    }
}
