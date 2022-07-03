package com.mahanko.finalproject.model.entity.menu;

import com.mahanko.finalproject.model.entity.AbstractEntity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The type MenuItem class.
 */
public class MenuItem extends AbstractEntity<Long> {
    private String name;
    private BigDecimal cost;
    private String pictureBase64;
    private long sectionId;
    private final Set<Ingredient> ingredients;
    private Integer hashCode;

    /**
     * Instantiates a new MenuItem.
     */
    public MenuItem() {
        ingredients = new HashSet<>();
    }

    /**
     * Set the menu section id to which the menu item belongs.
     *
     * @param sectionId the menu section id to which the menu item belongs.
     */
    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * Get the menu section id to which the menu item belongs.
     *
     * @return the menu section id to which the menu item belongs.
     */
    public long getSectionId() {
        return sectionId;
    }

    /**
     * Get the menu item cost.
     *
     * @return the menu item cost.
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * Set the menu item cost.
     *
     * @param cost the menu item cost.
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * Get the menu item name.
     *
     * @return the menu item name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the menu item name.
     *
     * @param name the menu item name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the menu item picture string encoded base64.
     *
     * @return the menu item picture string encoded base64.
     */
    public String getPictureBase64() {
        return pictureBase64;
    }

    /**
     * Set the menu item picture string encoded base64.
     *
     * @param pictureBase64 the menu item picture string encoded base64.
     */
    public void setPictureBase64(String pictureBase64) {
        this.pictureBase64 = pictureBase64;
    }

    /**
     * Get the menu item ingredients.
     *
     * @return the menu item ingredients.
     */
    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * Add the ingredient into menu item.
     *
     * @param ingredient the ingredient.
     * @return true if menu item did not already contain the specified ingredient.
     */
    public boolean addIngredient(Ingredient ingredient) {
        return ingredients.add(ingredient);
    }

    /**
     * Remove the ingredient from menu item.
     *
     * @param ingredient the ingredient.
     * @return true ingredient was added.
     */
    public boolean removeIngredient(Ingredient ingredient) {
        return ingredients.remove(ingredient);
    }

    /**
     * Get sum the menu item ingredients calories.
     *
     * @return sum the menu item ingredients calories.
     */
    public double getCalories() {
        return Math.round(ingredients.stream().map(Ingredient::calcCalories).reduce(0d, Double::sum));
    }

    /**
     * Get sum the menu item ingredients weights.
     *
     * @return sum the menu item ingredients weights.
     */
    public double getWeight() {
        return ingredients.stream().map(Ingredient::getWeight).reduce(0d, Double::sum);
    }

    /**
     * Get sum the menu item ingredients proteins.
     *
     * @return sum the menu item ingredients proteins.
     */
    public double getProteins() {
        return Math.round(ingredients.stream().map(Ingredient::calcProteins).reduce(0d, Double::sum));
    }

    /**
     * Get sum the menu item ingredients fats.
     *
     * @return sum the menu item ingredients fats.
     */
    public double getFats() {
        return Math.round(ingredients.stream().map(Ingredient::calcFats).reduce(0d, Double::sum));
    }

    /**
     * Get sum the menu item ingredients carbohydrates.
     *
     * @return sum the menu item ingredients carbohydrates.
     */
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
        if (hashCode == null) {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (cost != null ? cost.hashCode() : 0);
            result = 31 * result + (pictureBase64 != null ? pictureBase64.hashCode() : 0);
            result = 31 * result + (int) (sectionId ^ (sectionId >>> 32));
            result = 31 * result + (ingredients != null ? ingredients.hashCode() : 0);
            hashCode = result;
        }

        return hashCode;
    }
}
