package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.*;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.controller.filter.CookieName;
import com.mahanko.finalproject.exception.CommandException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * The {@link Command} that sets localization to English.
 */
public class SetLocalizationToEnglishCommand implements Command {
    /**
     * Executes a command.
     *
     * @param request  The request
     * @param response The response
     * @return The router with type {@link Router.Type#REDIRECT} to previous page.
     * @throws CommandException the command exception
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router router = new Router(request.getParameter(ParameterType.PATH), Router.Type.REDIRECT);
        HttpSession session = request.getSession();
        session.setAttribute(AttributeType.LOCALE, LocaleType.ENGLISH);
        response.addCookie(new Cookie(CookieName.LOCALE, LocaleType.ENGLISH));
        return router;
    }
}
