package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.AttributeType;
import com.mahanko.finalproject.controller.LocaleType;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.controller.filter.CookieName;
import com.mahanko.finalproject.exception.CommandException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * The {@link Command} that sets localization to Russian.
 */
public class SetLocalizationToRussianCommand implements Command {
    /**
     * Executes a command.
     *
     * @param request  The request
     * @param response The responce
     * @return The router with type {@link Router.Type#REDIRECT} to previous page.
     * @throws CommandException the command exception
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router router = new Router(request.getParameter(ParameterType.PATH), Router.Type.REDIRECT);
        HttpSession session = request.getSession();
        session.setAttribute(AttributeType.LOCALE, LocaleType.RUSSIAN);
        response.addCookie(new Cookie(CookieName.LOCALE, LocaleType.RUSSIAN));
        return router;
    }
}