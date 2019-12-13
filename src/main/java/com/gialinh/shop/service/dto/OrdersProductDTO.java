package com.gialinh.shop.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.gialinh.shop.domain.OrdersProduct} entity.
 */
public class OrdersProductDTO implements Serializable {

    private Long id;

    private Integer quantity;

    private Double unitPrice;


    private Long ordersId;

    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Long ordersId) {
        this.ordersId = ordersId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrdersProductDTO ordersProductDTO = (OrdersProductDTO) o;
        if (ordersProductDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ordersProductDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrdersProductDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", unitPrice=" + getUnitPrice() +
            ", orders=" + getOrdersId() +
            ", product=" + getProductId() +
            "}";
    }
}
