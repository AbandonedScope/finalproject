package com.mahanko.finalproject.model.entity.menu;

import com.mahanko.finalproject.model.entity.AbstractEntity;

public class IngredientComponent extends AbstractEntity<Long> implements MenuComponent {
    private String name;
    private double cost;
    private double weight;
    private double proteins;
    private double fats;
    private double carbohydrates;
    private double calories;
    private String pictureString;

    public static IngredientBuilder newBuilder() {
        return new IngredientComponent(). new IngredientBuilder();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    @Override
    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    @Override
    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    @Override
    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public String getPicture() {
        return pictureString;
    }

    public void setPicture(String picture) {
        this.pictureString = picture;
    }

    public class IngredientBuilder {
        public IngredientBuilder setName(String name) {
            IngredientComponent.this.setName(name);
            return this;
        }

        public IngredientBuilder setCost(double cost) {
            IngredientComponent.this.setCost(cost);
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
