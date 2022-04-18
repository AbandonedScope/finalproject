package com.mahanko.finalproject.model.entity;

public class RoleEntity extends AbstractEntity<Integer> {
    private RoleType type = RoleType.GUEST;

    public RoleEntity() {
    }

    public RoleEntity(Integer id, RoleType type) {
        super(id);
        this.type = type;
    }

    public RoleType getType() {
        return type;
    }

    public void setType(RoleType type) {
        this.type = type;
    }
}
