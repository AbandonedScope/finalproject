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
import com.mahanko.finalproject.model.service.impl.CustomerServiceImpl;
import com.mahanko.finalproject.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UnblockCustomerCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String customerIdString = request.getParameter(ParameterType.CUSTOMER_ID);
        try {
            long customerId = Long.parseLong(customerIdString);
            CustomerService customerService = CustomerServiceImpl.getInstance();
            customerService.setBlocked(customerId, false);
            Optional<CustomerEntity> optionalCustomer = customerService.findById(customerId);
            if (optionalCustomer.isPresent()) {
                CustomerEntity customer = optionalCustomer.get();
                List<OrderEntity> customerOrders = OrderServiceImpl.getInstance().findOrdersByCustomerId(customerId);
                request.setAttribute(AttributeType.CUSTOMER, customer);
                if (!customerOrders.isEmpty()) {
                    request.setAttribute(AttributeType.CUSTOMER_ORDERS, customerOrders);
                }
            }
        } catch (NumberFormatException e) {
            logger.log(Level.ERROR, e);
            throw new CommandException(e);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return new Router(PagePath.CUSTOMER_INFO, Router.Type.FORWARD);
    }
}
