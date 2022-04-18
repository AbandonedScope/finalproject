package com.mahanko.finalproject.model.entity;

public abstract class AbstractEntity<T> {
    protected T id;

    protected AbstractEntity() {}
    protected AbstractEntity(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }
}
