package com.mahanko.finalproject.model.entity;

import java.util.Objects;

public class CustomerEntity extends AbstractEntity<Long> {
    // FIXME: 30.05.2022 to property file
    private static final int DEFAULT_LOYALTY_POINTS_AMOUNT = 5;
    private String name;
    private String surname;
    private String password;
    private String login;
    private int loyalPoints = DEFAULT_LOYALTY_POINTS_AMOUNT;
    private boolean blocked;
    private RoleType roleEntity;
    private Integer hashCode;

    public CustomerEntity() {
    }

    public CustomerEntity(Long id) {
        super(id);
    }

    public static CustomerBuilder newBuilder() {
        return new CustomerEntity().new CustomerBuilder();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerEntity)) return false;

        CustomerEntity customer = (CustomerEntity) o;

        if (loyalPoints != customer.loyalPoints) return false;
        if (blocked != customer.blocked) return false;
        if (!Objects.equals(name, customer.name)) return false;
        if (!Objects.equals(surname, customer.surname)) return false;
        if (!Objects.equals(password, customer.password)) return false;
        if (!Objects.equals(login, customer.login)) return false;
        return roleEntity == customer.roleEntity;
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (surname != null ? surname.hashCode() : 0);
            result = 31 * result + (password != null ? password.hashCode() : 0);
            result = 31 * result + (login != null ? login.hashCode() : 0);
            result = 31 * result + loyalPoints;
            result = 31 * result + (blocked ? 1 : 0);
            result = 31 * result + (roleEntity != null ? roleEntity.hashCode() : 0);
            hashCode = result;
        }

        return hashCode;
    }
}
