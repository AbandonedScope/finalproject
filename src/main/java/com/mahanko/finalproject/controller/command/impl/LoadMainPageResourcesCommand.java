package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.AttributeType;
import com.mahanko.finalproject.controller.PagePath;
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

public class LoadMainPageResourcesCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        MenuSectionService sectionService = MenuSectionServiceImpl.getInstance();
        List<MenuSection> sections;
        try {
            sections = sectionService.findAll();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute(AttributeType.SECTIONS, sections);
        return new Router(PagePath.MAIN, Router.Type.FORWARD);
    }
}
