package com.mahanko.finalproject.model.entity.menu;

import com.mahanko.finalproject.model.entity.AbstractEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IngredientComponent extends AbstractEntity<Long> implements MenuItemComponent {
    private static final Logger logger = LogManager.getLogger();
    private String name;
    private double weight;
    private double proteins;
    private double fats;
    private double carbohydrates;
    private double calories;
    private String pictureBase64;

    public static IngredientBuilder newBuilder() {
        return new IngredientComponent(). new IngredientBuilder();
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

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public String getPicture() {
        return pictureBase64;
    }

    public void setPicture(String picture) {
        this.pictureBase64 = picture;
    }

    public class IngredientBuilder {
        public IngredientBuilder setId(Long id) {
            IngredientComponent.this.id = id;
            return this;
        }

        public IngredientBuilder setName(String name) {
            IngredientComponent.this.setName(name);
            return this;
        }

        public IngredientBuilder setWeight(double weight) {
            IngredientComponent.this.setWeight(weight);
            return this;
        }

        public IngredientBuilder setProteins(double proteins) {
            IngredientComponent.this.setProteins(proteins);
            return this;
        }

        public IngredientBuilder setFats(double fats) {
            IngredientComponent.this.setFats(fats);
            return this;
        }

        public IngredientBuilder setCarbohydrates(double carbohydrates) {
            IngredientComponent.this.setCarbohydrates(carbohydrates);
            return this;
        }

        public IngredientBuilder setCalories(double calories) {
            IngredientComponent.this.setCalories(calories);
            return this;
        }

        public IngredientBuilder setPicture(String base64) {
            IngredientComponent.this.setPicture(base64);
            return this;
        }

        public IngredientComponent build() {
            return IngredientComponent.this;
        }
    }
}
