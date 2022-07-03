package com.mahanko.finalproject.controller.command.impl;

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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.mahanko.finalproject.controller.AttributeType.ORDER_CART;

/**
 * The {@link Command} that changes amount of certain meal in the customer cart by id.
 */
public class ChangeItemInCartAmountCommand implements Command {
    /**
     * Used for writing logs
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * Executes a command.
     *
     * @param request  The request
     * @param response The response
     * @return  The router with type {@link Router.Type#REDIRECT} to {@link PagePath#SHOPPING_CART}.
     * @throws CommandException the command exception
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        String amount = request.getParameter(ParameterType.MENU_ITEM_COUNT);
        String id = request.getParameter(ParameterType.MENU_ITEM_ID);
        Router route = new Router(PagePath.SHOPPING_CART);
        OrderEntity order = (OrderEntity) session.getAttribute(ORDER_CART);
        if (order == null) {
            logger.log(Level.ERROR, "The order was null");
            throw new CommandException("The order was null");
        }

        OrderService service = OrderServiceImpl.getInstance();
        try {
            service.setItemAmount(order, id, amount);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return route;
    }
}
