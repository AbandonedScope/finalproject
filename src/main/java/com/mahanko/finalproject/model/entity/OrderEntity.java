package com.mahanko.finalproject.model.entity;

import com.mahanko.finalproject.model.entity.menu.MenuItem;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class OrderEntity extends AbstractEntity<Long> {
    private Long userId;
    private Time servingTime;
    private final Map<MenuItem, Integer> items;

    public OrderEntity() {
        items = new HashMap<>();
    }

    public Set<Map.Entry<MenuItem, Integer>> getItems() {
        return items.entrySet();
    }

    public void addItem(MenuItem item, Integer count) {
        items.computeIfPresent(item, (curItem, prevCount) -> prevCount + count);
        items.putIfAbsent(item, count);
    }

    public void removeItem(MenuItem item) {
        items.remove(item);
    }

    public int getAmount(MenuItem item) {
        return items.getOrDefault(item, 0);
    }

    public double getCost() {
        return items.entrySet()
                .stream()
                .map(pair -> pair.getKey().getCost() * pair.getValue())
                .reduce(0d, Double::sum);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Time getServingTime() {
        return servingTime;
    }

    public void setServingTime(Time servingTime) {
        this.servingTime = servingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEntity that = (OrderEntity) o;

        if (!Objects.equals(userId, that.userId)) return false;
        if (!Objects.equals(servingTime, that.servingTime)) return false;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (servingTime != null ? servingTime.hashCode() : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        return result;
    }
}