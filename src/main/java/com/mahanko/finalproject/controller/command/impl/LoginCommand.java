package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.ParametersType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.model.service.CustomerService;
import com.mahanko.finalproject.model.service.impl.CustomerServiceImpl;
import com.mahanko.finalproject.validator.CustomValidator;
import com.mahanko.finalproject.validator.impl.CustomValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String login = request.getParameter(ParametersType.LOGIN.toString());
        String password = request.getParameter(ParametersType.PASSWORD.toString());
        CustomerService customerService = CustomerServiceImpl.getInstance();
        String page;
        HttpSession session = request.getSession();
        Router route = new Router();
        CustomValidator validator = new CustomValidatorImpl();
        try {
            CustomerEntity customer;
            if (validator.validateLogin(login)
                    && validator.validatePassword(password)
                    && (customer = customerService.authenticate(login, password)) != null) {
                session.setAttribute("user", customer); // FIXME: 09.04.2022  user into constant(i suppose)
                page = "pages/main.jsp"; // FIXME: 05.04.2022 into constant(class pagepass(requestParameterName, requestAttributeName))
            } else {
                request.setAttribute("login_msg", "Wrong login or password.");
                page = "index.jsp"; // FIXME: 05.04.2022 into constant
                route.setType(Router.Type.FORWARD);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        route.setPage(page);
        return route;
    }
}
