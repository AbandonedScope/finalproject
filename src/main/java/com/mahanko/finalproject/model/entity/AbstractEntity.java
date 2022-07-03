package com.mahanko.finalproject.model.entity;

/**
 * The type CustomEntity class. The super class for all entity classes.
 * @param <T> the type of the id of the entity.
 */
public abstract class AbstractEntity<T> {
    protected T id;

    protected AbstractEntity() {}
    protected AbstractEntity(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }
    public void setId(T id) {
        this.id = id;
    }
}
