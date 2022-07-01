package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.service.MenuSectionService;
import com.mahanko.finalproject.model.service.impl.MenuSectionServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RemoveMenuSectionCommand extends AsynchronousCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router router;
        try {
            String sectionIdString = request.getParameter(ParameterType.MENU_SECTION_ID);
            int sectionId = Integer.parseInt(sectionIdString);
            MenuSectionService menuSectionService = MenuSectionServiceImpl.getInstance();
            boolean removed = menuSectionService.remove(sectionId);
            router = fillResponse(response, removed);
        } catch (NumberFormatException e) {
            logger.log(Level.ERROR, e);
            throw new CommandException(e);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return router;
    }
}
