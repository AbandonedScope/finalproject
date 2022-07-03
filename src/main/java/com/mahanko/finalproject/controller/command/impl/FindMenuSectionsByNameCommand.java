package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.AttributeType;
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
/**
 * The {@link Command} that is used to find menu section by its name.
 */
public class FindMenuSectionsByNameCommand implements Command {
    /**
     * Executes a command.
     *
     * @param request  The request
     * @param response The response
     * @return  The router with type {@link Router.Type#FORWARD} to {@link PagePath#MODIFY_MENU_SECTION}.
     * @throws CommandException the command exception
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router route = new Router(PagePath.MODIFY_MENU_SECTION, Router.Type.FORWARD);
        String sectionName = request.getParameter(ParameterType.MENU_SECTION_NAME);
        MenuSectionService service = MenuSectionServiceImpl.getInstance();
        List<MenuSection> sections;
        try {
            sections = service.findByName(sectionName);
            request.setAttribute(AttributeType.SECTIONS, sections);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return route;
    }
}
