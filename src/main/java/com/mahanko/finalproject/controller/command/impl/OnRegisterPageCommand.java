package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The {@link Command} that redirects to registration page.
 */
public class OnRegisterPageCommand implements Command {
    /**
     * Executes a command.
     *
     * @param request  The request
     * @param response The response
     * @return  with type {@link Router.Type#REDIRECT} to {@link PagePath#REGISTRATION}.
     * @throws CommandException the command exception
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        return new Router(PagePath.REGISTRATION);
    }
}
