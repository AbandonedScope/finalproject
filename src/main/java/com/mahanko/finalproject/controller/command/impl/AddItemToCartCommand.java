package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.OrderEntity;
import com.mahanko.finalproject.model.entity.menu.MenuItem;
import com.mahanko.finalproject.model.service.MenuItemService;
import com.mahanko.finalproject.model.service.impl.MenuItemServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import static com.mahanko.finalproject.controller.AttributeType.ORDER_CART;

/**
 * The {@link AsynchronousCommand} that add new or additional {@link MenuItem}
 * into customer's {@link OrderEntity}.
 */
public class AddItemToCartCommand extends AsynchronousCommand {

    /**
     *  Executes a command.
     * @param request  The request
     * @param response The response
     * @return The router  with type {@link Router.Type#NONE}
     * @throws CommandException the command exception
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router router = new Router(PagePath.MAIN, Router.Type.REDIRECT);
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
            Optional<MenuItem> optionalMeal = service.findById(itemId);
            if (optionalMeal.isPresent()) {
                item = optionalMeal.get();
                order.addItem(item, count);
            } else {
                fillResponse(response, false);
            }
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException(e);
        }

        session.setAttribute(ORDER_CART, order);
        return router;
    }
}
