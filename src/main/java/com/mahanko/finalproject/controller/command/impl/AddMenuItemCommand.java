package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.model.entity.menu.MenuItemComposite;
import com.mahanko.finalproject.model.entity.menu.MenuItemCompositeLevel;
import com.mahanko.finalproject.model.service.MenuItemService;
import com.mahanko.finalproject.model.service.impl.MenuItemServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.math.BigDecimal;

public class AddMenuItemCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        MenuItemComposite menuItem = new MenuItemComposite(MenuItemCompositeLevel.MENU_ITEM);
        // FIXME: 22.04.2022 Into constants
        menuItem.setName(request.getParameter("name"));
        menuItem.setCost(BigDecimal.valueOf(Double.parseDouble(request.getParameter("cost"))));
        MenuItemService menuItemService = new MenuItemServiceImpl();
        return null;
    }
}
