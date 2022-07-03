package com.mahanko.finalproject.controller.filter.permission;

import com.mahanko.finalproject.controller.command.CommandType;

import java.util.Set;

/**
 * The enum User permission.
 */
public enum UserPermission {
    /**
     * Admin user permission.
     */
    ADMIN(Set.of(CommandType.ADD_INGREDIENT,
            CommandType.ADD_ITEM_TO_CART,
            CommandType.ADD_MENU_ITEM,
            CommandType.ADD_MENU_SECTION,
            CommandType.ADD_ORDER,
            CommandType.ADD_USER,
            CommandType.BLOCK_CUSTOMER,
            CommandType.CHANGE_ITEM_IN_CART_AMOUNT,
            CommandType.DEFAULT,
            CommandType.FIND_CUSTOMER_BY_NAME,
            CommandType.FIND_INGREDIENTS_BY_NAME,
            CommandType.FIND_MENU_ITEMS_BY_NAME,
            CommandType.FIND_MENU_SECTIONS_BY_NAME,
            CommandType.GET_CUSTOMER_INFO,
            CommandType.GET_ORDER_INFORMATION,
            CommandType.LOGIN,
            CommandType.LOGOUT,
            CommandType.MODIFY_INGREDIENT,
            CommandType.MODIFY_MENU_ITEM,
            CommandType.MODIFY_MENU_SECTION,
            CommandType.REMOVE_INGREDIENT,
            CommandType.REMOVE_MENU_ITEM,
            CommandType.REMOVE_MENU_SECTION,
            CommandType.SET_CUSTOMER_BLOCKED,
            CommandType.SET_CUSTOMER_ROLE_ADMIN,
            CommandType.SET_CUSTOMER_ROLE_CUSTOMER,
            CommandType.SET_LOCALIZATION_TO_ENGLISH,
            CommandType.SET_LOCALIZATION_TO_RUSSIAN,
            CommandType.SET_ORDER_SERVED,
            CommandType.SET_ORDER_TAKEN,
            CommandType.UNBLOCK_CUSTOMER,
            CommandType.LOAD_MAIN_PAGE_RESOURCES,
            CommandType.REMOVE_MENU_ITEM_FROM_CART,
            CommandType.ON_ORDERS_PAGE,
            CommandType.ON_CUSTOMER_ORDERS_PAGE,
            CommandType.ON_ADD_MENU_ITEM,
            CommandType.ON_CART_PAGE)),
    /**
     * Customer user permission.
     */
    CUSTOMER(Set.of(CommandType.ADD_ITEM_TO_CART,
            CommandType.ADD_ORDER,
            CommandType.ADD_USER,
            CommandType.CHANGE_ITEM_IN_CART_AMOUNT,
            CommandType.DEFAULT,
            CommandType.LOGIN,
            CommandType.SET_LOCALIZATION_TO_ENGLISH,
            CommandType.SET_LOCALIZATION_TO_RUSSIAN,
            CommandType.LOAD_MAIN_PAGE_RESOURCES,
            CommandType.REMOVE_MENU_ITEM_FROM_CART,
            CommandType.ON_CUSTOMER_ORDERS_PAGE,
            CommandType.ON_CART_PAGE)),
    /**
     * Guest user permission.
     */
    GUEST(Set.of(CommandType.ADD_ITEM_TO_CART,
            CommandType.ADD_USER,
            CommandType.CHANGE_ITEM_IN_CART_AMOUNT,
            CommandType.DEFAULT,
            CommandType.LOGIN,
            CommandType.SET_LOCALIZATION_TO_ENGLISH,
            CommandType.SET_LOCALIZATION_TO_RUSSIAN,
            CommandType.LOAD_MAIN_PAGE_RESOURCES,
            CommandType.REMOVE_MENU_ITEM_FROM_CART,
            CommandType.ON_CART_PAGE));

    private final Set<CommandType> commands;

    UserPermission(Set<CommandType> commands) {
        this.commands = commands;
    }

    /**
     * Determines whether user is permitted to execute certain command type or not.
     * @param commandType the command type, user wants to execute
     * @return true - if user has a permission to execution this command type, otherwise false
     */
    public boolean isPermitted(CommandType commandType) {
        return commands.stream().anyMatch(commandType::equals);
    }
}
