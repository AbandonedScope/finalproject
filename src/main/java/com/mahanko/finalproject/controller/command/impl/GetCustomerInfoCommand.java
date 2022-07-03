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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * The {@link Command} that is used to find information about customer and customer's orders by its id.
 */
public class GetCustomerInfoCommand implements Command {
    /**
     * Used for writing logs
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * Executes a command.
     *
     * @param request  The request
     * @param response The response
     * @return The router with type {@link Router.Type#FORWARD} to {@link PagePath#CUSTOMER_INFO} in case if customer with certain id was found, otherwise with type {@link Router.Type#REDIRECT} to {@link PagePath#CUSTOMER_FIND}.
     * @throws CommandException the command exception
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router router = new Router(PagePath.CUSTOMER_INFO, Router.Type.FORWARD);
        String customerIdString = request.getParameter(ParameterType.CUSTOMER_ID);
        try {
            long customerId = Long.parseLong(customerIdString);
            CustomerService customerService = CustomerServiceImpl.getInstance();
            OrderService orderService = OrderServiceImpl.getInstance();
            Optional<CustomerEntity> optionalCustomer = customerService.findById(customerId);
            if (optionalCustomer.isPresent()) {
                List<OrderEntity> customerOrders = orderService.findOrdersByCustomerId(customerId);
                request.setAttribute(AttributeType.CUSTOMER, optionalCustomer.get());
                if (!customerOrders.isEmpty()) {
                    request.setAttribute(AttributeType.CUSTOMER_ORDERS, customerOrders);
                }
            } else {
                router = new Router(PagePath.CUSTOMER_FIND);
            }
        } catch (NumberFormatException e) {
            logger.log(Level.ERROR, e);
            throw new CommandException(e);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return router;
    }
}
