package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SetLocalizationToEnglishCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router router = new Router(request.getParameter("path"), Router.Type.REDIRECT);
        HttpSession session = request.getSession();
        // FIXME: 01.05.2022 into constants
        session.setAttribute("language", "en_US");
        return router;
    }
}
