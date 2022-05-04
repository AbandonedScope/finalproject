package com.mahanko.finalproject.model.entity.menu;

import com.mahanko.finalproject.model.entity.AbstractEntity;

import java.math.BigDecimal;
import java.util.HashSet;

public class MenuItemComposite extends AbstractEntity<Long> implements MenuItemComponent {
    private MenuItemCompositeLevel level;
    private String name;
    private BigDecimal cost;
    private String description;
    private String pictureBase64;
    private MenuSection section;
    private HashSet<MenuItemComponent> ingredients;

    public MenuItemComposite(MenuItemCompositeLevel level) {
        this.level = level;
        ingredients = new HashSet<>();
    }

    public void setSection(MenuSection section) {
        this.section = section;
    }

    public MenuSection getSection() {
        return section;
    }

    public MenuItemCompositeLevel getLevel() {
        return level;
    }

    public void setLevel(MenuItemCompositeLevel level) {
        this.level = level;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureBase64() {
        return pictureBase64;
    }

    public void setPictureBase64(String pictureBase64) {
        this.pictureBase64 = pictureBase64;
    }

    public HashSet<MenuItemComponent> getIngredients() {
        return ingredients;
    }

    public boolean addIngredient(IngredientComponent ingredient) {
        return ingredients.add(ingredient);
    }

    public boolean removeIngredient(IngredientComponent ingredient) {
        return ingredients.remove(ingredient);
    }

    public double getCalories() {
        return ingredients.stream().map(MenuItemComponent::getCalories).reduce(0d, Double::sum);
    }

    public double getWeight() {
        return ingredients.stream().map(MenuItemComponent::getWeight).reduce(0d, Double::sum);
    }

    public double getProteins() {
        return ingredients.stream().map(MenuItemComponent::getProteins).reduce(0d, Double::sum);
    }

    public double getFats() {
        return ingredients.stream().map(MenuItemComponent::getFats).reduce(0d, Double::sum);
    }

    public double getCarbohydrates() {
        return ingredients.stream().map(MenuItemComponent::getCarbohydrates).reduce(0d, Double::sum);
    }
}
