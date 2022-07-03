package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.AttributeType;
import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.model.entity.OrderEntity;
import com.mahanko.finalproject.model.service.OrderService;
import com.mahanko.finalproject.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

/**
 * The {@link Command} that finds and puts into request customers active orders.
 */
public class OnCustomerOrdersPageCommand implements Command {
    /**
     * Executes a command.
     *
     * @param request  The request
     * @param response The response
     * @return The router with type {@link Router.Type#FORWARD} to {@link PagePath#USER_ORDERS}.
     * @throws CommandException the command exception
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        CustomerEntity customer = (CustomerEntity) session.getAttribute(AttributeType.USER);
        OrderService service = OrderServiceImpl.getInstance();
        try {
            List<OrderEntity> userOrders = service.findActiveByCustomerId(customer.getId());
            request.setAttribute(ParameterType.USER_ORDERS, userOrders);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return new Router(PagePath.USER_ORDERS, Router.Type.FORWARD);
    }
}
