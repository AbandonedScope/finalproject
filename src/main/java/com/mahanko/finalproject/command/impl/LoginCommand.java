package com.mahanko.finalproject.command.impl;

import com.mahanko.finalproject.Router;
import com.mahanko.finalproject.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.service.CustomerService;
import com.mahanko.finalproject.service.impl.CustomerServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LoginCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter("login");// FIXME: 05.04.2022 into constant(separate class with parameters of request) 
        String password = request.getParameter("pass");// FIXME: 05.04.2022 into constant(separate class with parameters of request)
        CustomerService customerService = CustomerServiceImpl.getInstance();
        String page;
        HttpSession session = request.getSession();
        try {
            if(customerService.authenticate(login, password))
            {
                request.setAttribute("user", login);
                session.setAttribute("user_name", login); // FIXME: 09.04.2022  user_name into constant(i suppose)
                page = "pages/main.jsp"; // FIXME: 05.04.2022 into constant(class pagepass(requestParameterName, requestAttributeName))
            } else {
                request.setAttribute("login_msg", "incorrect login or password.");
                page = "index.jsp"; // FIXME: 05.04.2022 into constant
            }

            session.setAttribute("current_page", page); // FIXME: 09.04.2022 into constant(i suppose)
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return new Router(page);
    }
}
