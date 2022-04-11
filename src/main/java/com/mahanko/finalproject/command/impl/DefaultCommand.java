package com.mahanko.finalproject.command.impl;

import com.mahanko.finalproject.Router;
import com.mahanko.finalproject.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(); // FIXME: 05.04.2022 into constant
    }
}
