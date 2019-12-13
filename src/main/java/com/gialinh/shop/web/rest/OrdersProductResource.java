package com.gialinh.shop.web.rest;

import com.gialinh.shop.service.OrdersProductService;
import com.gialinh.shop.web.rest.errors.BadRequestAlertException;
import com.gialinh.shop.service.dto.OrdersProductDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.gialinh.shop.domain.OrdersProduct}.
 */
@RestController
@RequestMapping("/api")
public class OrdersProductResource {

    private final Logger log = LoggerFactory.getLogger(OrdersProductResource.class);

    private static final String ENTITY_NAME = "ordersProduct";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdersProductService ordersProductService;

    public OrdersProductResource(OrdersProductService ordersProductService) {
        this.ordersProductService = ordersProductService;
    }

    /**
     * {@code POST  /orders-products} : Create a new ordersProduct.
     *
     * @param ordersProductDTO the ordersProductDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordersProductDTO, or with status {@code 400 (Bad Request)} if the ordersProduct has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/orders-products")
    public ResponseEntity<OrdersProductDTO> createOrdersProduct(@RequestBody OrdersProductDTO ordersProductDTO) throws URISyntaxException {
        log.debug("REST request to save OrdersProduct : {}", ordersProductDTO);
        if (ordersProductDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordersProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdersProductDTO result = ordersProductService.save(ordersProductDTO);
        return ResponseEntity.created(new URI("/api/orders-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /orders-products} : Updates an existing ordersProduct.
     *
     * @param ordersProductDTO the ordersProductDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordersProductDTO,
     * or with status {@code 400 (Bad Request)} if the ordersProductDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordersProductDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/orders-products")
    public ResponseEntity<OrdersProductDTO> updateOrdersProduct(@RequestBody OrdersProductDTO ordersProductDTO) throws URISyntaxException {
        log.debug("REST request to update OrdersProduct : {}", ordersProductDTO);
        if (ordersProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrdersProductDTO result = ordersProductService.save(ordersProductDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ordersProductDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /orders-products} : get all the ordersProducts.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordersProducts in body.
     */
    @GetMapping("/orders-products")
    public ResponseEntity<List<OrdersProductDTO>> getAllOrdersProducts(Pageable pageable) {
        log.debug("REST request to get a page of OrdersProducts");
        Page<OrdersProductDTO> page = ordersProductService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /orders-products/:id} : get the "id" ordersProduct.
     *
     * @param id the id of the ordersProductDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordersProductDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/orders-products/{id}")
    public ResponseEntity<OrdersProductDTO> getOrdersProduct(@PathVariable Long id) {
        log.debug("REST request to get OrdersProduct : {}", id);
        Optional<OrdersProductDTO> ordersProductDTO = ordersProductService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordersProductDTO);
    }

    /**
     * {@code DELETE  /orders-products/:id} : delete the "id" ordersProduct.
     *
     * @param id the id of the ordersProductDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/orders-products/{id}")
    public ResponseEntity<Void> deleteOrdersProduct(@PathVariable Long id) {
        log.debug("REST request to delete OrdersProduct : {}", id);
        ordersProductService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
