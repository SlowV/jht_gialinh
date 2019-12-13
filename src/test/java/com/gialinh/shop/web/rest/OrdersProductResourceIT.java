package com.gialinh.shop.web.rest;

import com.gialinh.shop.ShopGiaLinhApp;
import com.gialinh.shop.domain.OrdersProduct;
import com.gialinh.shop.repository.OrdersProductRepository;
import com.gialinh.shop.service.OrdersProductService;
import com.gialinh.shop.service.dto.OrdersProductDTO;
import com.gialinh.shop.service.mapper.OrdersProductMapper;
import com.gialinh.shop.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.gialinh.shop.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OrdersProductResource} REST controller.
 */
@SpringBootTest(classes = ShopGiaLinhApp.class)
public class OrdersProductResourceIT {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Double DEFAULT_UNIT_PRICE = 1D;
    private static final Double UPDATED_UNIT_PRICE = 2D;

    @Autowired
    private OrdersProductRepository ordersProductRepository;

    @Autowired
    private OrdersProductMapper ordersProductMapper;

    @Autowired
    private OrdersProductService ordersProductService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restOrdersProductMockMvc;

    private OrdersProduct ordersProduct;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrdersProductResource ordersProductResource = new OrdersProductResource(ordersProductService);
        this.restOrdersProductMockMvc = MockMvcBuilders.standaloneSetup(ordersProductResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdersProduct createEntity(EntityManager em) {
        OrdersProduct ordersProduct = new OrdersProduct()
            .quantity(DEFAULT_QUANTITY)
            .unitPrice(DEFAULT_UNIT_PRICE);
        return ordersProduct;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdersProduct createUpdatedEntity(EntityManager em) {
        OrdersProduct ordersProduct = new OrdersProduct()
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE);
        return ordersProduct;
    }

    @BeforeEach
    public void initTest() {
        ordersProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrdersProduct() throws Exception {
        int databaseSizeBeforeCreate = ordersProductRepository.findAll().size();

        // Create the OrdersProduct
        OrdersProductDTO ordersProductDTO = ordersProductMapper.toDto(ordersProduct);
        restOrdersProductMockMvc.perform(post("/api/orders-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersProductDTO)))
            .andExpect(status().isCreated());

        // Validate the OrdersProduct in the database
        List<OrdersProduct> ordersProductList = ordersProductRepository.findAll();
        assertThat(ordersProductList).hasSize(databaseSizeBeforeCreate + 1);
        OrdersProduct testOrdersProduct = ordersProductList.get(ordersProductList.size() - 1);
        assertThat(testOrdersProduct.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrdersProduct.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void createOrdersProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ordersProductRepository.findAll().size();

        // Create the OrdersProduct with an existing ID
        ordersProduct.setId(1L);
        OrdersProductDTO ordersProductDTO = ordersProductMapper.toDto(ordersProduct);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdersProductMockMvc.perform(post("/api/orders-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrdersProduct in the database
        List<OrdersProduct> ordersProductList = ordersProductRepository.findAll();
        assertThat(ordersProductList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrdersProducts() throws Exception {
        // Initialize the database
        ordersProductRepository.saveAndFlush(ordersProduct);

        // Get all the ordersProductList
        restOrdersProductMockMvc.perform(get("/api/orders-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordersProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getOrdersProduct() throws Exception {
        // Initialize the database
        ordersProductRepository.saveAndFlush(ordersProduct);

        // Get the ordersProduct
        restOrdersProductMockMvc.perform(get("/api/orders-products/{id}", ordersProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ordersProduct.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrdersProduct() throws Exception {
        // Get the ordersProduct
        restOrdersProductMockMvc.perform(get("/api/orders-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrdersProduct() throws Exception {
        // Initialize the database
        ordersProductRepository.saveAndFlush(ordersProduct);

        int databaseSizeBeforeUpdate = ordersProductRepository.findAll().size();

        // Update the ordersProduct
        OrdersProduct updatedOrdersProduct = ordersProductRepository.findById(ordersProduct.getId()).get();
        // Disconnect from session so that the updates on updatedOrdersProduct are not directly saved in db
        em.detach(updatedOrdersProduct);
        updatedOrdersProduct
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE);
        OrdersProductDTO ordersProductDTO = ordersProductMapper.toDto(updatedOrdersProduct);

        restOrdersProductMockMvc.perform(put("/api/orders-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersProductDTO)))
            .andExpect(status().isOk());

        // Validate the OrdersProduct in the database
        List<OrdersProduct> ordersProductList = ordersProductRepository.findAll();
        assertThat(ordersProductList).hasSize(databaseSizeBeforeUpdate);
        OrdersProduct testOrdersProduct = ordersProductList.get(ordersProductList.size() - 1);
        assertThat(testOrdersProduct.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrdersProduct.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingOrdersProduct() throws Exception {
        int databaseSizeBeforeUpdate = ordersProductRepository.findAll().size();

        // Create the OrdersProduct
        OrdersProductDTO ordersProductDTO = ordersProductMapper.toDto(ordersProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdersProductMockMvc.perform(put("/api/orders-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrdersProduct in the database
        List<OrdersProduct> ordersProductList = ordersProductRepository.findAll();
        assertThat(ordersProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrdersProduct() throws Exception {
        // Initialize the database
        ordersProductRepository.saveAndFlush(ordersProduct);

        int databaseSizeBeforeDelete = ordersProductRepository.findAll().size();

        // Delete the ordersProduct
        restOrdersProductMockMvc.perform(delete("/api/orders-products/{id}", ordersProduct.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrdersProduct> ordersProductList = ordersProductRepository.findAll();
        assertThat(ordersProductList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdersProduct.class);
        OrdersProduct ordersProduct1 = new OrdersProduct();
        ordersProduct1.setId(1L);
        OrdersProduct ordersProduct2 = new OrdersProduct();
        ordersProduct2.setId(ordersProduct1.getId());
        assertThat(ordersProduct1).isEqualTo(ordersProduct2);
        ordersProduct2.setId(2L);
        assertThat(ordersProduct1).isNotEqualTo(ordersProduct2);
        ordersProduct1.setId(null);
        assertThat(ordersProduct1).isNotEqualTo(ordersProduct2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdersProductDTO.class);
        OrdersProductDTO ordersProductDTO1 = new OrdersProductDTO();
        ordersProductDTO1.setId(1L);
        OrdersProductDTO ordersProductDTO2 = new OrdersProductDTO();
        assertThat(ordersProductDTO1).isNotEqualTo(ordersProductDTO2);
        ordersProductDTO2.setId(ordersProductDTO1.getId());
        assertThat(ordersProductDTO1).isEqualTo(ordersProductDTO2);
        ordersProductDTO2.setId(2L);
        assertThat(ordersProductDTO1).isNotEqualTo(ordersProductDTO2);
        ordersProductDTO1.setId(null);
        assertThat(ordersProductDTO1).isNotEqualTo(ordersProductDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ordersProductMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ordersProductMapper.fromId(null)).isNull();
    }
}
