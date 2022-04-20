package com.mahanko.finalproject.model.entity.menu;

import com.mahanko.finalproject.model.entity.AbstractEntity;

import java.util.HashSet;

public class MenuComposite<T extends MenuComponent> extends AbstractEntity<Long> implements MenuComponent {
    private MenuCompositeLevel level;
    private HashSet<T> components;

    @Override
    public double getCost() {
        return components.stream().map(MenuComponent::getCost).reduce(0d, Double::sum);
    }

    @Override
    public double getCalories() {
        return components.stream().map(MenuComponent::getCalories).reduce(0d, Double::sum);
    }

    @Override
    public double getWeight() {
        return components.stream().map(MenuComponent::getWeight).reduce(0d, Double::sum);
    }

    @Override
    public double getProteins() {
        return components.stream().map(MenuComponent::getProteins).reduce(0d, Double::sum);
    }

    @Override
    public double getFats() {
        return components.stream().map(MenuComponent::getFats).reduce(0d, Double::sum);
    }

    @Override
    public double getCarbohydrates() {
        return components.stream().map(MenuComponent::getCarbohydrates).reduce(0d, Double::sum);
    }
}
