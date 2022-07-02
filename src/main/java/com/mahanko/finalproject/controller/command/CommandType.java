package com.mahanko.finalproject.controller.command;

import com.mahanko.finalproject.controller.command.impl.*;
import com.mysql.cj.util.StringUtils;

import java.util.Arrays;

/**
 * The enum Command type.
 */
public enum CommandType {
    /**
     * The Register
     */
    REGISTER(new OnRegisterPageCommand()),
    /**
     * The Login.
     */
    LOGIN(new LoginCommand()),
    /**
     * The Logout.
     */
    LOGOUT(new LogoutCommand()),
    /**
     * The Default.
     */
    DEFAULT(new DefaultCommand()),
    /**
     * The Add new user.
     */
    ADD_USER(new AddUserCommand()),
    /**
     * The Add new ingredient.
     */
    ADD_INGREDIENT(new AddIngredientCommand()),
    /**
     * The Add new menu item.
     */
    ADD_MENU_ITEM(new AddMenuItemCommand()),
    /**
     * The Add new menu section.
     */
    ADD_MENU_SECTION(new AddMenuSectionCommand()),
    /**
     * The Set localization to english.
     */
    SET_LOCALIZATION_TO_ENGLISH(new SetLocalizationToEnglishCommand()),
    /**
     * The Set localization to russian.
     */
    SET_LOCALIZATION_TO_RUSSIAN(new SetLocalizationToRussianCommand()),
    /**
     * The Add menu item to cart.
     */
    ADD_ITEM_TO_CART(new AddItemToCartCommand()),
    /**
     * The Add new order into database.
     */
    ADD_ORDER(new AddOrderCommand()),
    /**
     * Move to the orders page.
     */
    ON_ORDERS_PAGE(new OnOrdersPageCommand()),
    /**
     * The Change amount of item in cart.
     */
    CHANGE_ITEM_IN_CART_AMOUNT(new ChangeItemInCartAmountCommand()),
    /**
     * The Remove menu item from cart.
     */
    REMOVE_MENU_ITEM_FROM_CART(new RemoveItemFromCartCommand()),
    /**
     * The Find menu items by name.
     */
    FIND_MENU_ITEMS_BY_NAME(new FindMenuItemsByNameCommand()),
    /**
     * The Find menu section by name.
     */
    FIND_MENU_SECTIONS_BY_NAME(new FindMenuSectionsByNameCommand()),
    /**
     * The Find ingredients by name.
     */
    FIND_INGREDIENTS_BY_NAME(new FindIngredientsByNameCommand()),
    /**
     * Modify menu item.
     */
    MODIFY_MENU_ITEM(new ModifyMenuItemCommand()),
    /**
     * Modify menu section.
     */
    MODIFY_MENU_SECTION(new ModifyMenuSectionCommand()),
    /**
     * Modify ingredient.
     */
    MODIFY_INGREDIENT(new ModifyIngredientCommand()),
    /**
     * The Get order information.
     */
    GET_ORDER_INFORMATION(new GetOrderInformationCommand()),
    /**
     * The Get customer information.
     */
    GET_CUSTOMER_INFO(new GetCustomerInfoCommand()),
    /**
     * The Set order taken.
     */
    SET_ORDER_TAKEN(new SetOrderTakenCommand()),
    /**
     * The Set order served.
     */
    SET_ORDER_SERVED(new SetOrderServedCommand()),
    /**
     * The Set customer blocked.
     */
    SET_CUSTOMER_BLOCKED(new SetCustomerBlockedCommand()),
    /**
     * The Remove ingredient.
     */
    REMOVE_INGREDIENT(new RemoveIngredientCommand()),
    /**
     * The Remove menu item.
     */
    REMOVE_MENU_ITEM(new RemoveMenuItemCommand()),
    /**
     * The Remove menu section.
     */
    REMOVE_MENU_SECTION(new RemoveMenuSectionCommand()),
    /**
     * To Block customer.
     */
    BLOCK_CUSTOMER(new BlockCustomerCommand()),
    /**
     * To Unblock customer.
     */
    UNBLOCK_CUSTOMER(new UnblockCustomerCommand()),
    /**
     * The Find customer by name.
     */
    FIND_CUSTOMER_BY_NAME(new FindCustomerByNameCommand()),
    /**
     * The Set user role admin.
     */
    SET_CUSTOMER_ROLE_ADMIN(new SetCustomerRoleAdminCommand()),
    /**
     * The Set user role customer.
     */
    SET_CUSTOMER_ROLE_CUSTOMER(new SetCustomerRoleCustomerCommand()),
    LOAD_MAIN_PAGE_RESOURCES(new LoadMainPageResourcesCommand()),
    ON_CUSTOMER_ORDERS_PAGE(new OnCustomerOrdersPageCommand()),
    ON_ADD_MENU_ITEM(new OnAddMenuItemPageCommand());

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    /**
     * Provide corresponding command or default command.
     *
     * @param commandStr the command
     * @return the command
     */
    public static CommandType of(String commandStr) {
        CommandType current = DEFAULT;
        if (!StringUtils.isNullOrEmpty(commandStr)) {
            String commandName = commandStr.toUpperCase().replace('-', '_');
            if (Arrays.stream(CommandType.values()).anyMatch(type -> type.name().equals(commandName))) {
                current = CommandType.valueOf(commandName);
            }
        }

        return current;
    }

    public Command getCommand() {
        return command;
    }
}
