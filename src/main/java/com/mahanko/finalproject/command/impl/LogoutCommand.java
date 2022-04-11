package com.mahanko.finalproject.command.impl;

import com.mahanko.finalproject.Router;
import com.mahanko.finalproject.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return new Router(); // FIXME: 09.04.2022 into constant
    }
}
