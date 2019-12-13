package com.gialinh.shop.service.mapper;

import com.gialinh.shop.domain.*;
import com.gialinh.shop.service.dto.OrdersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Orders} and its DTO {@link OrdersDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface OrdersMapper extends EntityMapper<OrdersDTO, Orders> {

    @Mapping(source = "customer.id", target = "customerId")
    OrdersDTO toDto(Orders orders);

    @Mapping(target = "ordersProducts", ignore = true)
    @Mapping(target = "removeOrdersProduct", ignore = true)
    @Mapping(source = "customerId", target = "customer")
    Orders toEntity(OrdersDTO ordersDTO);

    default Orders fromId(Long id) {
        if (id == null) {
            return null;
        }
        Orders orders = new Orders();
        orders.setId(id);
        return orders;
    }
}
