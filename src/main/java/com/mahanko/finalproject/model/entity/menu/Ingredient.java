package com.mahanko.finalproject.model.entity.menu;

import com.mahanko.finalproject.model.entity.AbstractEntity;

import java.util.Objects;

public class Ingredient extends AbstractEntity<Long> {
    public static final double BASIC_WEIGHT = 100d;
    private String name;
    private double weight;
    private double proteins;
    private double fats;
    private double carbohydrates;
    private double calories;
    private String pictureBase64;

    public static IngredientBuilder newBuilder() {
        return new Ingredient(). new IngredientBuilder();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getProteins() {
        return proteins;
    }

    public double calcProteins() {
        return proteins * weight / 100;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getFats() {
        return fats;
    }

    public double calcFats() {
        return fats * weight / 100;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public double calcCarbohydrates() {
        return carbohydrates * weight / 100;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public double getCalories() {
        return calories;
    }

    public double calcCalories() {
        return calories * weight / 100;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public String getPictureBase64() {
        return pictureBase64;
    }

    public void setPicture(String picture) {
        this.pictureBase64 = picture;
    }

    public class IngredientBuilder {
        public IngredientBuilder setId(Long id) {
            Ingredient.this.id = id;
            return this;
        }

        public IngredientBuilder setName(String name) {
            Ingredient.this.setName(name);
            return this;
        }

        public IngredientBuilder setWeight(double weight) {
            Ingredient.this.setWeight(weight);
            return this;
        }

        public IngredientBuilder setProteins(double proteins) {
            Ingredient.this.setProteins(proteins);
            return this;
        }

        public IngredientBuilder setFats(double fats) {
            Ingredient.this.setFats(fats);
            return this;
        }

        public IngredientBuilder setCarbohydrates(double carbohydrates) {
            Ingredient.this.setCarbohydrates(carbohydrates);
            return this;
        }

        public IngredientBuilder setCalories(double calories) {
            Ingredient.this.setCalories(calories);
            return this;
        }

        public IngredientBuilder setPicture(String base64) {
            Ingredient.this.setPicture(base64);
            return this;
        }

        public Ingredient build() {
            return Ingredient.this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingredient that = (Ingredient) o;

        if (Double.compare(that.weight, weight) != 0) return false;
        if (Double.compare(that.proteins, proteins) != 0) return false;
        if (Double.compare(that.fats, fats) != 0) return false;
        if (Double.compare(that.carbohydrates, carbohydrates) != 0) return false;
        if (Double.compare(that.calories, calories) != 0) return false;
        if (!Objects.equals(name, that.name)) return false;
        return Objects.equals(pictureBase64, that.pictureBase64);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(proteins);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(fats);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(carbohydrates);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(calories);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (pictureBase64 != null ? pictureBase64.hashCode() : 0);
        return result;
    }
}
