package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.service.MenuItemService;
import com.mahanko.finalproject.model.service.impl.MenuItemServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@link Command} that removes menu item from database, or hides it, in case if it is used in any order.
 */
public class RemoveMenuItemCommand extends AsynchronousCommand {
    /**
     * Used for writing logs
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * Executes a command.
     *
     * @param request  The request
     * @param response The responce
     * @return The router
     * @throws CommandException the command exception
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router router;
        try {
            String menuItemIdString = request.getParameter(ParameterType.MENU_ITEM_ID);
            long menuItemId = Long.parseLong(menuItemIdString);
            MenuItemService menuItemService = MenuItemServiceImpl.getInstance();
            boolean removed = menuItemService.remove(menuItemId);
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
