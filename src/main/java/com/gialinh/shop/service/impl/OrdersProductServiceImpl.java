package com.gialinh.shop.service.impl;

import com.gialinh.shop.service.OrdersProductService;
import com.gialinh.shop.domain.OrdersProduct;
import com.gialinh.shop.repository.OrdersProductRepository;
import com.gialinh.shop.service.dto.OrdersProductDTO;
import com.gialinh.shop.service.mapper.OrdersProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OrdersProduct}.
 */
@Service
@Transactional
public class OrdersProductServiceImpl implements OrdersProductService {

    private final Logger log = LoggerFactory.getLogger(OrdersProductServiceImpl.class);

    private final OrdersProductRepository ordersProductRepository;

    private final OrdersProductMapper ordersProductMapper;

    public OrdersProductServiceImpl(OrdersProductRepository ordersProductRepository, OrdersProductMapper ordersProductMapper) {
        this.ordersProductRepository = ordersProductRepository;
        this.ordersProductMapper = ordersProductMapper;
    }

    /**
     * Save a ordersProduct.
     *
     * @param ordersProductDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OrdersProductDTO save(OrdersProductDTO ordersProductDTO) {
        log.debug("Request to save OrdersProduct : {}", ordersProductDTO);
        OrdersProduct ordersProduct = ordersProductMapper.toEntity(ordersProductDTO);
        ordersProduct = ordersProductRepository.save(ordersProduct);
        return ordersProductMapper.toDto(ordersProduct);
    }

    /**
     * Get all the ordersProducts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrdersProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrdersProducts");
        return ordersProductRepository.findAll(pageable)
            .map(ordersProductMapper::toDto);
    }


    /**
     * Get one ordersProduct by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrdersProductDTO> findOne(Long id) {
        log.debug("Request to get OrdersProduct : {}", id);
        return ordersProductRepository.findById(id)
            .map(ordersProductMapper::toDto);
    }

    /**
     * Delete the ordersProduct by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrdersProduct : {}", id);
        ordersProductRepository.deleteById(id);
    }
}
