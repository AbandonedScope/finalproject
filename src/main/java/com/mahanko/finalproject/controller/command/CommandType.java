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
    SET_LOCALIZATION_TO_ENGLISH(new SetLocalizationToEnglishCommand()),
    SET_LOCALIZATION_TO_RUSSIAN(new SetLocalizationToRussianCommand()),
    ADD_ITEM_TO_CART(new AddItemToCartCommand()),
    ADD_ORDER(new AddOrderCommand()),
    ON_ORDERS_PAGE(new OnOrdersPageCommand()),
    CHANGE_ITEM_IN_CART_AMOUNT(new ChangeItemInCartAmountCommand()),
    REMOVE_MENU_ITEM_FROM_CART(new RemoveItemFromCartCommand()),
    FIND_MENU_ITEMS_BY_NAME(new FindMenuItemsByNameCommand()),
    FIND_MENU_SECTIONS_BY_NAME(new FindMenuSectionsByNameCommand()),
    FIND_INGREDIENTS_BY_NAME(new FindIngredientsByNameCommand()),
    MODIFY_MENU_ITEM(new ModifyMenuItemCommand()),
    MODIFY_MENU_SECTION(new ModifyMenuSectionCommand()),
    MODIFY_INGREDIENT(new ModifyIngredientCommand()),
    GET_ORDER_INFORMATION(new GetOrderInformationCommand()),
    GET_CUSTOMER_INFO(new GetCustomerInfoCommand()),
    SET_ORDER_TAKEN(new SetOrderTakenCommand()),
    SET_ORDER_SERVED(new SetOrderServedCommand()),
    SET_CUSTOMER_BLOCKED(new SetCustomerBlockedCommand()),
    REMOVE_INGREDIENT(new RemoveIngredientCommand()),
    REMOVE_MENU_ITEM(new RemoveMenuItemCommand()),
    REMOVE_MENU_SECTION(new RemoveMenuSectionCommand()),
    BLOCK_CUSTOMER(new BlockCustomerCommand()),
    UNBLOCK_CUSTOMER(new UnblockCustomerCommand());

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
