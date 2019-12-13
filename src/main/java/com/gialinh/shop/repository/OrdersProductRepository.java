package com.gialinh.shop.repository;
import com.gialinh.shop.domain.OrdersProduct;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrdersProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdersProductRepository extends JpaRepository<OrdersProduct, Long> {

}
