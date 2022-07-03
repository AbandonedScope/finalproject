package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.AttributeType;
import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.model.entity.PaymentType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * The {@link Command} that find and puts into request allowed payment types.
 */
public class OnCartPageCommand implements Command {
    /**
     * Executes a command.
     *
     * @param request  The request
     * @param response The response
     * @return The router with type {@link Router.Type#FORWARD} to {@link PagePath#SHOPPING_CART}.
     * @throws CommandException the command exception
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        var paymentTypes = Arrays.stream(PaymentType.values()).map(PaymentType::toString).collect(Collectors.toList());
        request.setAttribute(AttributeType.PAYMENT_TYPES, paymentTypes);
        return new Router(PagePath.SHOPPING_CART, Router.Type.FORWARD);
    }
}
