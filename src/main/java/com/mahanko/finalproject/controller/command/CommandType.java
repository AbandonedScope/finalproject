package com.mahanko.finalproject.controller.command;

import com.mahanko.finalproject.controller.command.impl.*;
import com.mysql.cj.util.StringUtils;

public enum CommandType {
    REGISTER(new OnRegisterPageCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    DEFAULT(new DefaultCommand()),
    ADD_USER(new AddUserCommand()),
    ADD_INGREDIENT(new AddIngredientCommand()),
    ADD_MENU_ITEM(new AddMenuItemCommand()),
    ADD_MENU_SECTION(new AddMenuSectionCommand()),
    ON_ADD_MENU_ITEM(new OnAddMenuItemPageCommand()),
    SET_LOCALIZATION_TO_ENGLISH(new SetLocalizationToEnglishCommand()),
    SET_LOCALIZATION_TO_RUSSIAN(new SetLocalizationToRussianCommand());



    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command define(String commandStr) {
        // FIXME: 01.05.2022 throw exception?
        CommandType current = DEFAULT;
        if (!StringUtils.isNullOrEmpty(commandStr)) {
            current = CommandType.valueOf(commandStr.toUpperCase().replace('-', '_'));
        }

        return current.command;
    }
}
