package com.mahanko.finalproject.controller.command;

import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The interface Command.
 */
@FunctionalInterface
public interface Command {
    /**
     *  Executes a command.
     * @param request  The request
     * @param response The responce
     * @return The router
     * @throws CommandException the command exception
     */
    Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}
