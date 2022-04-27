package com.mahanko.finalproject.controller.command.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.MenuSection;
import com.mahanko.finalproject.model.service.IngredientService;
import com.mahanko.finalproject.model.service.MenuSectionService;
import com.mahanko.finalproject.model.service.impl.IngredientServiceImpl;
import com.mahanko.finalproject.model.service.impl.MenuSectionServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class OnAddMenuItemPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        IngredientService service = new IngredientServiceImpl();
        Router router = new Router(PagePath.ADD_MENU_ITEM, Router.Type.FORWARD);
        try {
            Gson gson = new GsonBuilder().create();
            String jsonIngredients = gson.toJson(service.findAll());
            request.setAttribute(ParameterType.INGREDIENTS, jsonIngredients);
            MenuSectionService sectionService = new MenuSectionServiceImpl();
            // FIXME: 27.04.2022
            List<MenuSection> sections = sectionService.findAll();
            request.setAttribute("sections", sectionService.findAll());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return router;
    }
}
