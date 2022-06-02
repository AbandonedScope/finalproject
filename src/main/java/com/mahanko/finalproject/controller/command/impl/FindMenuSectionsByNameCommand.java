package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.MenuSection;
import com.mahanko.finalproject.model.service.MenuSectionService;
import com.mahanko.finalproject.model.service.impl.MenuSectionServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class FindMenuSectionsByNameCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router route = new Router(PagePath.MODIFY_MENU_SECTION, Router.Type.FORWARD);
        String sectionName = request.getParameter(ParameterType.MENU_SECTION_NAME);
        MenuSectionService service = MenuSectionServiceImpl.getInstance();
        List<MenuSection> sections;
        try {
            sections = service.findByName(sectionName);
            request.setAttribute(ParameterType.SECTIONS, sections);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return route;
    }
}