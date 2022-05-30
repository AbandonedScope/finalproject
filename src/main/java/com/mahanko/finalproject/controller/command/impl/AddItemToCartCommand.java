package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.OrderEntity;
import com.mahanko.finalproject.model.entity.menu.MenuItem;
import com.mahanko.finalproject.model.service.MenuItemService;
import com.mahanko.finalproject.model.service.impl.MenuItemServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static com.mahanko.finalproject.controller.AttributeType.ORDER_CART;

public class AddItemToCartCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        OrderEntity order;
        if (session.getAttribute(ORDER_CART) == null) {
            order = new OrderEntity();
        } else {
            order = (OrderEntity) session.getAttribute(ORDER_CART);
        }
        Long itemId = Long.parseLong(request.getParameter(ParameterType.MENU_ITEM_ID));
        Integer count = Integer.parseInt(request.getParameter(ParameterType.MENU_ITEM_COUNT));
        MenuItemService service = MenuItemServiceImpl.getInstance();
        MenuItem item;
        try {
            item = service.findById(itemId).orElseThrow(() -> new CommandException("No such item was found."));
            order.addItem(item, count);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        session.setAttribute(ORDER_CART, order);
        return new Router(PagePath.MAIN, Router.Type.FORWARD);
    }
}
