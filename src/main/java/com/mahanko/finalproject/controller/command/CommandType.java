package com.mahanko.finalproject.controller.command;

import com.mahanko.finalproject.controller.command.impl.*;

public enum CommandType {
    REGISTER(new RegisterCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    DEFAULT(new DefaultCommand()),
    ADD_USER(new AddUserCommand()),
    ADD_MENU_ITEM(new AddMenuItemCommand());

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command define(String commandStr) {
        CommandType current = CommandType.valueOf(commandStr.toUpperCase().replace('-', '_'));
        return current.command;
    }
}
