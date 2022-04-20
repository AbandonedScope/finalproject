package com.mahanko.finalproject.model.entity.menu;

public class MenuItemComposite extends MenuComposite<IngredientComponent> {
    private String name;
    private String pictureString;
    private MenuSectionComposite section;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return pictureString;
    }

    public void setPicture(String base64) {
        this.pictureString = pictureString;
    }

    public MenuSectionComposite getSection() {
        return section;
    }

    public void setSection(MenuSectionComposite section) {
        this.section = section;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
