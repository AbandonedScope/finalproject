package com.mahanko.finalproject.model.entity.menu;

import com.mahanko.finalproject.model.entity.AbstractEntity;

import java.util.Objects;

/**
 * The type Ingredient class.
 */
public class Ingredient extends AbstractEntity<Long> {
    /**
     * The weight of the ingredient that its calories, proteins, fats, carbohydrates base on.
     */
    public static final double BASIC_WEIGHT = 100d;
    private String name;
    private double weight;
    private double proteins;
    private double fats;
    private double carbohydrates;
    private double calories;
    private String pictureBase64;
    private Integer hashCode;

    /**
     * Instantiates a new IngredientBuilder.
     *
     * @return new IngredientBuilder.
     */
    public static IngredientBuilder newBuilder() {
        return new Ingredient().new IngredientBuilder();
    }

    /**
     * Get the ingredient name.
     *
     * @return the ingredient name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the ingredient name.
     *
     * @param name the ingredient name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the ingredient weight.
     *
     * @return the ingredient weight.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Set the ingredient weight.
     *
     * @param weight the ingredient weight.
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Get the ingredient proteins per 100 grams.
     *
     * @return the ingredient proteins per 100 grams.
     */
    public double getProteins() {
        return proteins;
    }

    /**
     * Get the whole ingredient proteins.
     *
     * @return the whole ingredient proteins.
     */
    public double calcProteins() {
        return proteins * weight / BASIC_WEIGHT;
    }

    /**
     * Set  the ingredient proteins per 100 grams.
     *
     * @param proteins the ingredient proteins per 100 grams.
     */
    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    /**
     * Get the ingredient fats per 100 grams.
     *
     * @return the ingredient fats per 100 grams.
     */
    public double getFats() {
        return fats;
    }

    /**
     * Get the whole ingredient fats.
     *
     * @return the whole ingredient fats.
     */
    public double calcFats() {
        return fats * weight / BASIC_WEIGHT;
    }

    /**
     * Set the ingredient fats per 100 grams.
     *
     * @param fats the ingredient fats per 100 grams.
     */
    public void setFats(double fats) {
        this.fats = fats;
    }

    /**
     * Get the ingredient carbohydrates per 100 grams.
     *
     * @return the ingredient carbohydrates per 100 grams.
     */
    public double getCarbohydrates() {
        return carbohydrates;
    }

    /**
     * Get the whole ingredient carbohydrates.
     *
     * @return the whole ingredient carbohydrates.
     */
    public double calcCarbohydrates() {
        return carbohydrates * weight / BASIC_WEIGHT;
    }

    /**
     * Set the ingredient carbohydrates per 100 grams.
     *
     * @param carbohydrates the ingredient carbohydrates per 100 grams.
     */
    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    /**
     * Get the ingredient calories per 100 grams.
     *
     * @return the ingredient calories per 100 grams.
     */
    public double getCalories() {
        return calories;
    }

    /**
     * Get the whole ingredient calories.
     *
     * @return the whole ingredient calories.
     */
    public double calcCalories() {
        return calories * weight / BASIC_WEIGHT;
    }

    /**
     * Set the ingredient calories per 100 grams.
     *
     * @param calories the ingredient calories per 100 grams.
     */
    public void setCalories(double calories) {
        this.calories = calories;
    }

    /**
     * Get the ingredient picture string encoded base64.
     *
     * @return the ingredient picture string encoded base64.
     */
    public String getPictureBase64() {
        return pictureBase64;
    }

    /**
     * Set the ingredient picture string encoded base64.
     *
     * @param picture the ingredient picture string encoded base64.
     */
    public void setPicture(String picture) {
        this.pictureBase64 = picture;
    }

    /**
     * The type IngredientBuilder class
     */
    public class IngredientBuilder {
        /**
         * Set the ingredient id
         *
         * @param id the ingredient id.
         * @return the builder.
         */
        public IngredientBuilder setId(Long id) {
            Ingredient.this.id = id;
            return this;
        }

        /**
         * Set the ingredient name.
         *
         * @param name the ingredient name.
         * @return the builder.
         */
        public IngredientBuilder setName(String name) {
            Ingredient.this.setName(name);
            return this;
        }

        /**
         * Set the ingredient weight.
         *
         * @param weight the ingredient weight.
         * @return the builder.
         */
        public IngredientBuilder setWeight(double weight) {
            Ingredient.this.setWeight(weight);
            return this;
        }

        /**
         * Set  the ingredient proteins per 100 grams.
         *
         * @param proteins the ingredient proteins per 100 grams.
         * @return the builder.
         */
        public IngredientBuilder setProteins(double proteins) {
            Ingredient.this.setProteins(proteins);
            return this;
        }

        /**
         * Set the ingredient fats per 100 grams.
         *
         * @param fats the ingredient fats per 100 grams.
         * @return the builder.
         */
        public IngredientBuilder setFats(double fats) {
            Ingredient.this.setFats(fats);
            return this;
        }

        /**
         * Set the ingredient carbohydrates per 100 grams.
         *
         * @param carbohydrates the ingredient carbohydrates per 100 grams.
         * @return the builder.
         */
        public IngredientBuilder setCarbohydrates(double carbohydrates) {
            Ingredient.this.setCarbohydrates(carbohydrates);
            return this;
        }

        /**
         * Set the ingredient calories per 100 grams.
         *
         * @param calories the ingredient calories per 100 grams.
         * @return the builder.
         */
        public IngredientBuilder setCalories(double calories) {
            Ingredient.this.setCalories(calories);
            return this;
        }

        /**
         * Set the ingredient picture string encoded base64.
         *
         * @param base64 the ingredient picture string encoded base64.
         * @return the builder.
         */
        public IngredientBuilder setPicture(String base64) {
            Ingredient.this.setPicture(base64);
            return this;
        }

        /**
         * Instantiates a new Ingredient.
         *
         * @return new Ingredient class object.
         */
        public Ingredient build() {
            return Ingredient.this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Ingredient that = (Ingredient) o;

        if (Double.compare(that.weight, weight) != 0) {
            return false;
        }

        if (Double.compare(that.proteins, proteins) != 0) {
            return false;
        }

        if (Double.compare(that.fats, fats) != 0) {
            return false;
        }

        if (Double.compare(that.carbohydrates, carbohydrates) != 0) {
            return false;
        }

        if (Double.compare(that.calories, calories) != 0) {
            return false;
        }

        if (!Objects.equals(name, that.name)) {
            return false;
        }

        return Objects.equals(pictureBase64, that.pictureBase64);
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
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
            hashCode = result;
        }

        return hashCode;
    }
}
