package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.OrderEntity;
import com.mahanko.finalproject.model.service.OrderService;
import com.mahanko.finalproject.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * The {@link Command} that is executes to find customer orders by its id.
 */
public class OnOrdersPageCommand implements Command {
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
        Router route = new Router(PagePath.ORDERS, Router.Type.FORWARD);
        OrderService service = OrderServiceImpl.getInstance();
        try {
            List<OrderEntity> orders = service.findAll();
            request.setAttribute(ParameterType.ORDERS, orders);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return route;
    }
}
