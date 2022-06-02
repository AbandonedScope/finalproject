package com.mahanko.finalproject.model.entity.menu;

import com.mahanko.finalproject.model.entity.AbstractEntity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MenuItem extends AbstractEntity<Long> {
    private String name;
    private BigDecimal cost;
    private String pictureBase64;
    private long sectionId;
    private final Set<Ingredient> ingredients;

    public MenuItem() {
        ingredients = new HashSet<>();
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    public long getSectionId() {
        return sectionId;
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

    public String getPictureBase64() {
        return pictureBase64;
    }

    public void setPictureBase64(String pictureBase64) {
        this.pictureBase64 = pictureBase64;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public boolean addIngredient(Ingredient ingredient) {
        return ingredients.add(ingredient);
    }

    public boolean removeIngredient(Ingredient ingredient) {
        return ingredients.remove(ingredient);
    }

    public double getCalories() {
        return Math.round(ingredients.stream().map(Ingredient::calcCalories).reduce(0d, Double::sum));
    }

    public double getWeight() {
        return ingredients.stream().map(Ingredient::getWeight).reduce(0d, Double::sum);
    }

    public double getProteins() {
        return Math.round(ingredients.stream().map(Ingredient::calcProteins).reduce(0d, Double::sum));
    }

    public double getFats() {
        return Math.round(ingredients.stream().map(Ingredient::calcFats).reduce(0d, Double::sum));
    }

    public double getCarbohydrates() {
        return Math.round(ingredients.stream().map(Ingredient::calcCarbohydrates).reduce(0d, Double::sum));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuItem menuItem = (MenuItem) o;

        if (sectionId != menuItem.sectionId) return false;
        if (!Objects.equals(name, menuItem.name)) return false;
        if (!Objects.equals(cost, menuItem.cost)) return false;
        if (!Objects.equals(pictureBase64, menuItem.pictureBase64))
            return false;
        return Objects.equals(ingredients, menuItem.ingredients);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (pictureBase64 != null ? pictureBase64.hashCode() : 0);
        result = 31 * result + (int) (sectionId ^ (sectionId >>> 32));
        result = 31 * result + (ingredients != null ? ingredients.hashCode() : 0);
        return result;
    }
}
