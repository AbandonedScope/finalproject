package com.mahanko.finalproject.controller.command;

import com.mahanko.finalproject.controller.command.impl.*;
import com.mysql.cj.util.StringUtils;

import java.util.Arrays;

public enum CommandType {
    REGISTER(new OnRegisterPageCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    DEFAULT(new DefaultCommand()),
    ADD_USER(new AddUserCommand()),
    ADD_INGREDIENT(new AddIngredientCommand()),
    ADD_MENU_ITEM(new AddMenuItemCommand()),
    ADD_MENU_SECTION(new AddMenuSectionCommand()),
    //ON_ADD_MENU_ITEM(new OnAddMenuItemPageCommand()),
    SET_LOCALIZATION_TO_ENGLISH(new SetLocalizationToEnglishCommand()),
    SET_LOCALIZATION_TO_RUSSIAN(new SetLocalizationToRussianCommand()),
    ADD_ITEM_TO_CART(new AddItemToCartCommand()),
    ADD_ORDER(new AddOrderCommand()),
    ON_ORDERS_PAGE(new OnOrdersPageCommand()),
    CHANGE_ITEM_IN_CART_AMOUNT(new ChangeItemInCartAmountCommand()),
    REMOVE_MENU_ITEM_FROM_CART(new RemoveItemFromCartCommand()),
    FIND_MENU_ITEMS_BY_NAME(new FindMenuItemsByName()),
    MODIFY_MENU_ITEM(new ModifyMenuItemCommand());


    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command of(String commandStr) {
        CommandType current = DEFAULT;
        if (!StringUtils.isNullOrEmpty(commandStr)) {
            String commandName = commandStr.toUpperCase().replace('-', '_');
            if (Arrays.stream(CommandType.values()).anyMatch(type -> type.name().equals(commandName))) {
                current = CommandType.valueOf(commandName);
            }
        }

        return current.command;
    }
}
