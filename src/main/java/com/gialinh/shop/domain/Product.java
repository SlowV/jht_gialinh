package com.gialinh.shop.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "description")
    private String description;

    @Column(name = "images")
    private String images;

    @Column(name = "is_sale")
    private Boolean isSale;

    @Column(name = "percent")
    private Integer percent;

    @Lob
    @Column(name = "detail")
    private String detail;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "product")
    private Set<OrdersProduct> ordersProducts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Category category;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Collection collection;

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

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public Product price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImages() {
        return images;
    }

    public Product images(String images) {
        this.images = images;
        return this;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Boolean isIsSale() {
        return isSale;
    }

    public Product isSale(Boolean isSale) {
        this.isSale = isSale;
        return this;
    }

    public void setIsSale(Boolean isSale) {
        this.isSale = isSale;
    }

    public Integer getPercent() {
        return percent;
    }

    public Product percent(Integer percent) {
        this.percent = percent;
        return this;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public String getDetail() {
        return detail;
    }

    public Product detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Product createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Product updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public Product deletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getStatus() {
        return status;
    }

    public Product status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<OrdersProduct> getOrdersProducts() {
        return ordersProducts;
    }

    public Product ordersProducts(Set<OrdersProduct> ordersProducts) {
        this.ordersProducts = ordersProducts;
        return this;
    }

    public Product addOrdersProduct(OrdersProduct ordersProduct) {
        this.ordersProducts.add(ordersProduct);
        ordersProduct.setProduct(this);
        return this;
    }

    public Product removeOrdersProduct(OrdersProduct ordersProduct) {
        this.ordersProducts.remove(ordersProduct);
        ordersProduct.setProduct(null);
        return this;
    }

    public void setOrdersProducts(Set<OrdersProduct> ordersProducts) {
        this.ordersProducts = ordersProducts;
    }

    public Category getCategory() {
        return category;
    }

    public Product category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Collection getCollection() {
        return collection;
    }

    public Product collection(Collection collection) {
        this.collection = collection;
        return this;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", description='" + getDescription() + "'" +
            ", images='" + getImages() + "'" +
            ", isSale='" + isIsSale() + "'" +
            ", percent=" + getPercent() +
            ", detail='" + getDetail() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", status=" + getStatus() +
            "}";
    }
}
