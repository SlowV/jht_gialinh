package com.gialinh.shop.service.mapper;

import com.gialinh.shop.domain.*;
import com.gialinh.shop.service.dto.OrdersProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdersProduct} and its DTO {@link OrdersProductDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrdersMapper.class, ProductMapper.class})
public interface OrdersProductMapper extends EntityMapper<OrdersProductDTO, OrdersProduct> {

    @Mapping(source = "orders.id", target = "ordersId")
    @Mapping(source = "product.id", target = "productId")
    OrdersProductDTO toDto(OrdersProduct ordersProduct);

    @Mapping(source = "ordersId", target = "orders")
    @Mapping(source = "productId", target = "product")
    OrdersProduct toEntity(OrdersProductDTO ordersProductDTO);

    default OrdersProduct fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrdersProduct ordersProduct = new OrdersProduct();
        ordersProduct.setId(id);
        return ordersProduct;
    }
}
