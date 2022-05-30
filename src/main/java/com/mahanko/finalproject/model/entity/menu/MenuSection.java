package com.mahanko.finalproject.model.entity.menu;

import com.mahanko.finalproject.model.entity.AbstractEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MenuSection extends AbstractEntity<Integer> {
    private String name;
    private List<MenuItem> menuItems = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean addItem(MenuItem item) {
        return menuItems.add(item);
    }

    public boolean addItems(Collection<MenuItem> items) {
        return menuItems.addAll(items);
    }

    public boolean removeItem(MenuItem item) {
        return menuItems.remove(item);
    }

    public MenuItem getItem(int index) {
        return menuItems.get(index);
    }

    public List<MenuItem> getItems() {
        return List.copyOf(menuItems);
    }

    public int itemsCount() {
        return menuItems.size();
    }
}
