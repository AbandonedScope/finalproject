package com.mahanko.finalproject.model.entity;

import com.mahanko.finalproject.model.entity.menu.MenuItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The type OrderEntity class.
 */
public class OrderEntity extends AbstractEntity<Long> {
    private Long userId;
    private LocalDateTime servingTime;
    private LocalDateTime creationTime;
    private BigDecimal orderedCost;
    private boolean isTaken;
    private boolean isServed;
    private PaymentType paymentType;
    private final Map<MenuItem, Integer> items;
    private Integer hashCode;

    /**
     * Instantiates a new OrderEntity.
     */
    public OrderEntity() {
        items = new HashMap<>();
    }

    /**
     * Get order menu items and they amount.
     * @return the set of (menu item, amount) pairs.
     */
    public Set<Map.Entry<MenuItem, Integer>> getItems() {
        return items.entrySet();
    }

    /**
     * Add menu item of certain count to order.
     * @param item the menu item.
     * @param count the count of items.
     */
    public void addItem(MenuItem item, Integer count) {
        items.computeIfPresent(item, (curItem, prevCount) -> prevCount + count);
        items.putIfAbsent(item, count);
    }

    /**
     * Remove menu item from order.
     * @param item the menu item to remove.
     * @return the count of removed items.
     */
    public Integer removeItem(MenuItem item) {
        return items.remove(item);
    }

    /**
     * Get count of certain menu items in order.
     * @param item the menu item.
     * @return the count of certain menu items.
     */
    public int getAmount(MenuItem item) {
        return items.getOrDefault(item, 0);
    }

    /**
     * Get sum of costs of menu items in order.
     * @return the sum of costs of menu items in order.
     */
    public BigDecimal getCost() {
        return items.entrySet()
                .stream()
                .map(pair -> pair.getKey().getCost().multiply(BigDecimal.valueOf(pair.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Get id of the owner of the order.
     * @return the owner id.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Set id of the owner of the order.
     * @param userId the owner id.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Get serving date and time of the order.
     * @return the serving date and time.
     */
    public LocalDateTime getServingTime() {
        return servingTime;
    }

    /**
     * Set serving date and time of the order.
     * @param servingTime the serving date and time.
     */
    public void setServingTime(LocalDateTime servingTime) {
        this.servingTime = servingTime;
    }

    /**
     * Get creation date and time of the order.
     * @return the serving date and time.
     */
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    /**
     * Set creation date and time of the order.
     * @param creationTime the creation date and time of the order.
     */
    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * Get order taken sate.
     * @return the taken state of the order.
     */
    public boolean getTaken() {
        return isTaken;
    }

    /**
     * Set order taken sate.
     * @param taken the order taken sate.
     */
    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    /**
     * Get order served sate.
     * @return the order served sate.
     */
    public boolean getServed() {
        return isServed;
    }

    /**
     * Set order served sate.
     * @param served the order served sate.
     */
    public void setServed(boolean served) {
        isServed = served;
    }

    /**
     * Get order payment type.
     * @return the order payment type.
     */
    public PaymentType getPaymentType() {
        return paymentType;
    }

    /**
     * Set order payment type.
     * @param paymentType the order payment type.
     */
    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * Get order cost. If order cost was set return that value.
     * @return the cost of the order.
     */
    public BigDecimal getOrderedCost() {
        return orderedCost == null ? getCost() : orderedCost;
    }

    /**
     * Set order cost.
     * @param orderedCost the order cost.
     */
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
        if (hashCode == null) {
            int result = userId != null ? userId.hashCode() : 0;
            result = 31 * result + (servingTime != null ? servingTime.hashCode() : 0);
            result = 31 * result + (items != null ? items.hashCode() : 0);
            hashCode = result;
        }

        return hashCode;
    }
}