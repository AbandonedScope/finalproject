package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.service.OrderService;
import com.mahanko.finalproject.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@link Command} that updates in database order served status to true.
 */
public class SetOrderServedCommand  implements Command {
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
        try {
            String orderIdString = request.getParameter(ParameterType.ORDER_ID);
            long orderId = Long.parseLong(orderIdString);
            OrderService orderService = OrderServiceImpl.getInstance();
            orderService.setServed(orderId);
        } catch (NumberFormatException e) {
            logger.log(Level.ERROR, e);
            throw new CommandException(e);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return new Router(PagePath.ORDERS, Router.Type.REDIRECT);
    }
}
