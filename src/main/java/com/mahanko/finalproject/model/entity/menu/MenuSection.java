package com.mahanko.finalproject.model.entity.menu;

import com.mahanko.finalproject.model.entity.AbstractEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * The type MenuSection class.
 */
public class MenuSection extends AbstractEntity<Integer> {
    private String name;
    private List<MenuItem> menuItems = new ArrayList<>();
    private Integer hashCode;

    /**
     * Get the section name.
     *
     * @return the section name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the section name.
     *
     * @param name the section name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Add menu item to the section.
     *
     * @param item the menu item.
     * @return true if item was added.
     */
    public boolean addItem(MenuItem item) {
        return menuItems.add(item);
    }

    /**
     * Add all the menu items from the collection.
     *
     * @param items the collection of the menu items.
     * @return true any item was added
     */
    public boolean addItems(Collection<MenuItem> items) {
        return menuItems.addAll(items);
    }

    /**
     * Remove menu item from the section.
     *
     * @param item the menu item.
     * @return true if menu item was removed.
     */
    public boolean removeItem(MenuItem item) {
        return menuItems.remove(item);
    }

    /**
     * Get all section menu items.
     *
     * @return the list og all section menu items.
     */
    public List<MenuItem> getItems() {
        return List.copyOf(menuItems);
    }

    /**
     * Get the count of the menu items section contains.
     *
     * @return the count of the menu items section contains.
     */
    public int itemsCount() {
        return menuItems.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuSection)) {
            return false;
        }

        MenuSection section = (MenuSection) o;

        if (!Objects.equals(name, section.name)) {
            return false;
        }

        return menuItems.equals(section.menuItems);
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + menuItems.hashCode();
            hashCode = result;
        }

        return hashCode;
    }
}
