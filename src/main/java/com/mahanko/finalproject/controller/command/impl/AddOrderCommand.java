package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.AttributeType;
import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.RequestParameters;
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

import static com.mahanko.finalproject.controller.AttributeType.ORDER_CART;
import static com.mahanko.finalproject.controller.ParameterType.*;

public class AddOrderCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router route = new Router(PagePath.MAIN);
        HttpSession session = request.getSession();
        RequestParameters params = new RequestParameters();
        OrderEntity order = (OrderEntity)session.getAttribute(ORDER_CART);
        CustomerEntity customer = (CustomerEntity)session.getAttribute(AttributeType.USER);
        params.put(USER_ID, customer.getId().toString());
        params.put(ORDER_TIME, request.getParameter(ORDER_TIME));
        OrderService service = new OrderServiceImpl();
        try {
            if (!service.insertNew(params, order)) {
                params.fillRequestWithValidations(request);
                route.setType(Router.Type.FORWARD);
                route.setPage(PagePath.ORDER);
            } else {
                session.setAttribute(ORDER_CART, new OrderEntity());
            }
        } catch (ServiceException e) {
            throw  new CommandException(e);
        }

        return route;
    }
}
