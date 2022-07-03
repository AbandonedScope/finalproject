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
import com.mahanko.finalproject.model.service.CustomerService;
import com.mahanko.finalproject.model.service.OrderService;
import com.mahanko.finalproject.model.service.impl.CustomerServiceImpl;
import com.mahanko.finalproject.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

/**
 * The {@link Command} that is used to find information about order by its id.
 */
public class GetOrderInformationCommand implements Command {
    /**
     * Executes a command.
     *
     * @param request  The request
     * @param response The responce
     * @return The router with type {@link Router.Type#FORWARD} to {@link PagePath#ORDER_INFO} in case of success, otherwise with type {@link Router.Type#REDIRECT} to {@link PagePath#ORDERS}.
     * @throws CommandException the command exception
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router router = new Router(PagePath.ORDERS, Router.Type.REDIRECT);
        String idSting = request.getParameter(ParameterType.ORDER_ID);
        OrderService orderService = OrderServiceImpl.getInstance();
        try {
            long orderId = Long.parseLong(idSting);
            Optional<OrderEntity> optionalOrder = orderService.findById(orderId);
            if (optionalOrder.isPresent()) {
                OrderEntity order = optionalOrder.get();
                CustomerService customerService = CustomerServiceImpl.getInstance();
                Optional<CustomerEntity> optionalCustomer = customerService.findById(order.getUserId());
                if (optionalCustomer.isPresent()) {
                    request.setAttribute(AttributeType.ORDER, order);
                    request.setAttribute(AttributeType.ORDER_CUSTOMER, optionalCustomer.get());
                    router = new Router(PagePath.ORDER_INFO, Router.Type.FORWARD);
                }
            }
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException(e);
        }

        return router;
    }
}
