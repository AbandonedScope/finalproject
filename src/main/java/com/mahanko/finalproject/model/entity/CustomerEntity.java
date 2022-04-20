package com.mahanko.finalproject.model.entity;

public class CustomerEntity extends AbstractEntity<Long> {
    private static final int DEFAULT_LOYALTY_POINTS_AMOUNT = 5;
    private String name;
    private String surname;
    private String password;
    private String login;
    private int loyalPoints = DEFAULT_LOYALTY_POINTS_AMOUNT;
    private boolean blocked;
    private RoleType roleEntity;

    public CustomerEntity() {}

    public CustomerEntity(Long id) {
        super(id);
    }

    public static CustomerBuilder newBuilder() {
        return  new CustomerEntity().new CustomerBuilder();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setLoyalPoints(int loyalPoints) {
        this.loyalPoints = loyalPoints;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public void setRole(RoleType roleEntity) {
        this.roleEntity = roleEntity;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public int getLoyalPoints() {
        return loyalPoints;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public RoleType getRole() {
        return roleEntity;
    }

    public class CustomerBuilder {
        public CustomerBuilder setId(Long id) {
            CustomerEntity.this.id = id;
            return this;
        }

        public CustomerBuilder setName(String name) {
            CustomerEntity.this.name = name;
            return this;
        }

        public CustomerBuilder setSurname(String surname) {
            CustomerEntity.this.surname = surname;
            return this;
        }

        public CustomerBuilder setPassword(String password) {
            CustomerEntity.this.password = password;
            return this;
        }

        public CustomerBuilder setLogin(String login) {
            CustomerEntity.this.login = login;
            return this;
        }

        public CustomerBuilder setLoyaltyPoints(int loyaltyPoints) {
            CustomerEntity.this.loyalPoints = loyaltyPoints;
            return this;
        }

        public CustomerBuilder setBlocked(boolean blocked) {
            CustomerEntity.this.blocked = blocked;
            return this;
        }

        public CustomerBuilder setRole(RoleType roleEntity) {
            CustomerEntity.this.roleEntity = roleEntity;
            return this;
        }

        public CustomerEntity build() {
            return CustomerEntity.this;
        }
    }
}
