package com.mahanko.finalproject.model.entity;

import java.util.Objects;

/**
 * The type CustomerEntity class.
 */
public class CustomerEntity extends AbstractEntity<Long> {
    private static final int DEFAULT_LOYALTY_POINTS_AMOUNT = 5;
    private String name;
    private String surname;
    private String password;
    private String login;
    private int loyaltyPoints = DEFAULT_LOYALTY_POINTS_AMOUNT;
    private boolean blocked;
    private RoleType roleEntity;
    private Integer hashCode;

    /**
     * Instantiates a new CustomerEntity.
     */
    public CustomerEntity() {
    }

    /**
     * Instantiates a new CustomerEntity with given id.
     * @param id the id of new customer.
     */
    public CustomerEntity(Long id) {
        super(id);
    }

    /**
     * Get the new builder of CustomerEntity class.
     * @return the builder.
     */
    public static CustomerBuilder newBuilder() {
        return new CustomerEntity().new CustomerBuilder();
    }

    /**
     * Set the name.
     * @param name the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the surname.
     * @param surname the surname.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Set the password.
     * @param password the password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set the login.
     * @param login the login.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Set the loyal points.
     * @param loyaltyPoints the loyal points.
     */
    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    /**
     * Set the blocked state.
     * @param blocked the blocked state.
     */
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    /**
     * Set the role.
     * @param role the role.
     */
    public void setRole(RoleType role) {
        this.roleEntity = role;
    }

    /**
     * Get the name.
     * @return The name String
     */
    public String getName() {
        return name;
    }

    /**
     * Get the surname.
     * @return The surname String.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Get the password.
     * @return The password String.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get the login.
     * @return the login String.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Get the loyal points.
     * @return The loyal points amount.
     */
    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    /**
     * get the blocked state.
     * @return The blocked state.
     */
    public boolean isBlocked() {
        return blocked;
    }

    /**
     * Get the role.
     * @return The role.
     */
    public RoleType getRole() {
        return roleEntity;
    }

    /**
     * The type CustomerBuilder. Used for building new CustomerEntity object.
     */
    public class CustomerBuilder {
        /**
         * Set the customer id.
         * @param id the customer id.
         * @return the builder.
         */
        public CustomerBuilder setId(Long id) {
            CustomerEntity.this.id = id;
            return this;
        }

        /**
         * Set the customer name.
         * @param name the customer name.
         * @return the builder.
         */
        public CustomerBuilder setName(String name) {
            CustomerEntity.this.name = name;
            return this;
        }

        /**
         * Set the customer surname.
         * @param surname the customer surname.
         * @return the builder.
         */
        public CustomerBuilder setSurname(String surname) {
            CustomerEntity.this.surname = surname;
            return this;
        }

        /**
         * Set the customer password.
         * @param password the customer password.
         * @return the builder.
         */
        public CustomerBuilder setPassword(String password) {
            CustomerEntity.this.password = password;
            return this;
        }

        /**
         * Set the customer login.
         * @param login the customer login.
         * @return the builder.
         */
        public CustomerBuilder setLogin(String login) {
            CustomerEntity.this.login = login;
            return this;
        }

        /**
         * Set the customer loyalty points.
         * @param loyaltyPoints the customer loyalty points.
         * @return the builder.
         */
        public CustomerBuilder setLoyaltyPoints(int loyaltyPoints) {
            CustomerEntity.this.loyaltyPoints = loyaltyPoints;
            return this;
        }

        /**
         * Set the customer blocked state.
         * @param blocked the customer blocked state.
         * @return the builder.
         */
        public CustomerBuilder setBlocked(boolean blocked) {
            CustomerEntity.this.blocked = blocked;
            return this;
        }

        /**
         * Set the customer role.
         * @param roleEntity the customer role.
         * @return the builder.
         */
        public CustomerBuilder setRole(RoleType roleEntity) {
            CustomerEntity.this.roleEntity = roleEntity;
            return this;
        }

        /**
         * Build new CustomerEntity object.
         * @return new CustomerEntity object.
         */
        public CustomerEntity build() {
            return CustomerEntity.this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerEntity)) return false;

        CustomerEntity customer = (CustomerEntity) o;

        if (loyaltyPoints != customer.loyaltyPoints) return false;
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
            result = 31 * result + loyaltyPoints;
            result = 31 * result + (blocked ? 1 : 0);
            result = 31 * result + (roleEntity != null ? roleEntity.hashCode() : 0);
            hashCode = result;
        }

        return hashCode;
    }
}
