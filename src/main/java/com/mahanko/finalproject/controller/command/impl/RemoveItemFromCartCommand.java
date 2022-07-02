package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.AttributeType;
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
import jakarta.servlet.http.HttpSession;

/**
 * The {@link Command} that removes menu item from customer order.
 */
public class RemoveItemFromCartCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router route = new Router(PagePath.SHOPPING_CART);
        HttpSession session = request.getSession();
        OrderService service = OrderServiceImpl.getInstance();
        OrderEntity order = (OrderEntity) session.getAttribute(AttributeType.ORDER_CART);
        String menuItemId = request.getParameter(ParameterType.MENU_ITEM_ID);
        try {
            service.removeItem(order, menuItemId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return route;
    }
}
