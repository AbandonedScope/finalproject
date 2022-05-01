package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
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
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AddMenuItemCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String MENU_ITEM_ADD_SUCCESS = "Menu item was added successfully.";
    private static final String MENU_ITEM_ADD_FAILED = "Menu item wasn't added. Probably item with such name already exists";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            HttpSession session = request.getSession();
            RequestParameters parameters = new RequestParameters();
            List<String> ingredientIds = Arrays.stream(request.getParameterValues(ParameterType.INGREDIENT_ID)).collect(Collectors.toList());
            List<String> ingredientWeights = Arrays.stream(request.getParameterValues(ParameterType.INGREDIENT_WEIGHT)).collect(Collectors.toList());
            parameters.put(ParameterType.INGREDIENT_ID, ingredientIds);
            parameters.put(ParameterType.INGREDIENT_WEIGHT, ingredientWeights);

            Part filePart = request.getPart(ParameterType.MENU_ITEM_PICTURE);
            byte[] pictureBytes = filePart.getInputStream().readAllBytes();
            String menuItemPicture = CustomPictureEncoder.arrayToBase64(pictureBytes);
            parameters.put(ParameterType.MENU_ITEM_PICTURE, menuItemPicture);

            parameters.put(ParameterType.MENU_ITEM_PICTURE_NAME, filePart.getSubmittedFileName());
            parameters.put(ParameterType.MENU_ITEM_PICTURE_SIZE, String.valueOf(filePart.getSize()));
            parameters.put(ParameterType.MENU_ITEM_NAME, request.getParameter(ParameterType.MENU_ITEM_NAME));
            parameters.put(ParameterType.MENU_ITEM_COST, request.getParameter(ParameterType.MENU_ITEM_COST));
            parameters.put(ParameterType.MENU_ITEM_DESCRIPTION, request.getParameter(ParameterType.MENU_ITEM_DESCRIPTION));
            parameters.put(ParameterType.MENU_ITEM_SECTION_ID, request.getParameter(ParameterType.MENU_ITEM_SECTION_ID));

            MenuItemService menuItemService = new MenuItemServiceImpl();
            if (menuItemService.insertNew(parameters)) {
                session.setAttribute(ParameterType.MENU_ITEM_ADD_MESSAGE, MENU_ITEM_ADD_SUCCESS);
            } else {
                // FIXME: 01.05.2022 validation messages?
                session.setAttribute(ParameterType.MENU_ITEM_ADD_MESSAGE, MENU_ITEM_ADD_FAILED);
            }

        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
            throw new CommandException(e);
        }

        return new Router(PagePath.ADD_MENU_ITEM, Router.Type.REDIRECT);
    }
}
