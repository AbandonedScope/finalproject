package com.mahanko.finalproject.controller.command.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.service.IngredientService;
import com.mahanko.finalproject.model.service.MenuSectionService;
import com.mahanko.finalproject.model.service.impl.IngredientServiceImpl;
import com.mahanko.finalproject.model.service.impl.MenuSectionServiceImpl;
import com.mahanko.finalproject.validator.impl.IngredientValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class OnAddMenuItemPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        IngredientService service = new IngredientServiceImpl();
        Router router = new Router(PagePath.ADD_MENU_ITEM, Router.Type.REDIRECT);
        try {
            Gson gson = new GsonBuilder().create();
            String jsonIngredients = gson.toJson(service.findAll());
            MenuSectionService sectionService = new MenuSectionServiceImpl();
            // FIXME: 27.04.2022
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.setAttribute(ParameterType.SECTIONS, sectionService.findAll());
                session.setAttribute(ParameterType.INGREDIENTS, jsonIngredients);
            }

        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return router;
    }
}
