package com.mahanko.finalproject.model.entity.menu;

import com.mahanko.finalproject.model.entity.AbstractEntity;

import java.util.List;

public class MenuSection extends AbstractEntity<Integer> {
    private String name;
    private List<MenuItemComposite> menuItems;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
