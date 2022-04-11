package com.mahanko.finalproject.command;

import com.mahanko.finalproject.command.impl.AddUserCommand;
import com.mahanko.finalproject.command.impl.DefaultCommand;
import com.mahanko.finalproject.command.impl.LoginCommand;
import com.mahanko.finalproject.command.impl.LogoutCommand;

public enum CommandType {
    ADD_USER(new AddUserCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    DEFAULT(new DefaultCommand());
    Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command define(String commandStr) {
        CommandType current = CommandType.valueOf(commandStr.toUpperCase().replace('-', '_'));
        return current.command;
    }
}
