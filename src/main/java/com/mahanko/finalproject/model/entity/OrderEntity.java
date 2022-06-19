package com.mahanko.finalproject.model.entity;

import com.mahanko.finalproject.model.entity.menu.MenuItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class OrderEntity extends AbstractEntity<Long> {
    private Long userId;
    private LocalDateTime servingTime;
    private LocalDateTime creationTime;
    private BigDecimal orderedCost;
    private boolean isTaken;
    private PaymentType paymentType;
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

    public Integer removeItem(MenuItem item) {
        return items.remove(item);
    }

    public int getAmount(MenuItem item) {
        return items.getOrDefault(item, 0);
    }

    public BigDecimal getCost() {
        return items.entrySet()
                .stream()
                .map(pair -> pair.getKey().getCost().multiply(BigDecimal.valueOf(pair.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getServingTime() {
        return servingTime;
    }

    public void setServingTime(LocalDateTime servingTime) {
        this.servingTime = servingTime;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public boolean getTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getOrderedCost() {
        return orderedCost == null ? getCost() : orderedCost;
    }

    public void setOrderedCost(BigDecimal orderedCost) {
        this.orderedCost = orderedCost;
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