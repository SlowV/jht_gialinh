package com.gialinh.shop.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "ship_email", nullable = false)
    private String shipEmail;

    @NotNull
    @Column(name = "ship_phone", nullable = false)
    private String shipPhone;

    @NotNull
    @Column(name = "ship_address", nullable = false)
    private String shipAddress;

    @OneToMany(mappedBy = "customer")
    private Set<Orders> orders = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("customers")
    private Accounts accounts;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Customer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShipEmail() {
        return shipEmail;
    }

    public Customer shipEmail(String shipEmail) {
        this.shipEmail = shipEmail;
        return this;
    }

    public void setShipEmail(String shipEmail) {
        this.shipEmail = shipEmail;
    }

    public String getShipPhone() {
        return shipPhone;
    }

    public Customer shipPhone(String shipPhone) {
        this.shipPhone = shipPhone;
        return this;
    }

    public void setShipPhone(String shipPhone) {
        this.shipPhone = shipPhone;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public Customer shipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
        return this;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public Set<Orders> getOrders() {
        return orders;
    }

    public Customer orders(Set<Orders> orders) {
        this.orders = orders;
        return this;
    }

    public Customer addOrders(Orders orders) {
        this.orders.add(orders);
        orders.setCustomer(this);
        return this;
    }

    public Customer removeOrders(Orders orders) {
        this.orders.remove(orders);
        orders.setCustomer(null);
        return this;
    }

    public void setOrders(Set<Orders> orders) {
        this.orders = orders;
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public Customer accounts(Accounts accounts) {
        this.accounts = accounts;
        return this;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shipEmail='" + getShipEmail() + "'" +
            ", shipPhone='" + getShipPhone() + "'" +
            ", shipAddress='" + getShipAddress() + "'" +
            "}";
    }
}
