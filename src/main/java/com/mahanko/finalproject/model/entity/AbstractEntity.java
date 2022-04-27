package com.mahanko.finalproject.model.entity;

public abstract class AbstractEntity<K> {
    protected K id;

    protected AbstractEntity() {}
    protected AbstractEntity(K id) {
        this.id = id;
    }

    public K getId() {
        return id;
    }
    public void setId(K id) {
        this.id = id;
    }
}
