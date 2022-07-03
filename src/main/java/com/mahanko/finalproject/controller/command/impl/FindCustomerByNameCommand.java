package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.AttributeType;
import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.model.service.CustomerService;
import com.mahanko.finalproject.model.service.impl.CustomerServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * The {@link Command} that is used to find customer by its name, surname and login.
 */
public class FindCustomerByNameCommand implements Command {
    /**
     * Executes a command.
     *
     * @param request  The request
     * @param response The response
     * @return  The router with type {@link Router.Type#FORWARD} to {@link PagePath#CUSTOMER_FIND}.
     * @throws CommandException the command exception
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String name = request.getParameter(ParameterType.CUSTOMER_NAME);
            CustomerService customerService = CustomerServiceImpl.getInstance();
            List<CustomerEntity> customers = customerService.findByName(name);
            request.setAttribute(AttributeType.CUSTOMERS, customers);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return new Router(PagePath.CUSTOMER_FIND, Router.Type.FORWARD);
    }
}
