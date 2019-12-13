package com.gialinh.shop.web.rest;

import com.gialinh.shop.ShopGiaLinhApp;
import com.gialinh.shop.domain.Collection;
import com.gialinh.shop.repository.CollectionRepository;
import com.gialinh.shop.service.CollectionService;
import com.gialinh.shop.service.dto.CollectionDTO;
import com.gialinh.shop.service.mapper.CollectionMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.gialinh.shop.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CollectionResource} REST controller.
 */
@SpringBootTest(classes = ShopGiaLinhApp.class)
public class CollectionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGES = "AAAAAAAAAA";
    private static final String UPDATED_IMAGES = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private CollectionService collectionService;

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

    private MockMvc restCollectionMockMvc;

    private Collection collection;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CollectionResource collectionResource = new CollectionResource(collectionService);
        this.restCollectionMockMvc = MockMvcBuilders.standaloneSetup(collectionResource)
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
    public static Collection createEntity(EntityManager em) {
        Collection collection = new Collection()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .images(DEFAULT_IMAGES)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT)
            .status(DEFAULT_STATUS);
        return collection;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collection createUpdatedEntity(EntityManager em) {
        Collection collection = new Collection()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .images(UPDATED_IMAGES)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .status(UPDATED_STATUS);
        return collection;
    }

    @BeforeEach
    public void initTest() {
        collection = createEntity(em);
    }

    @Test
    @Transactional
    public void createCollection() throws Exception {
        int databaseSizeBeforeCreate = collectionRepository.findAll().size();

        // Create the Collection
        CollectionDTO collectionDTO = collectionMapper.toDto(collection);
        restCollectionMockMvc.perform(post("/api/collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collectionDTO)))
            .andExpect(status().isCreated());

        // Validate the Collection in the database
        List<Collection> collectionList = collectionRepository.findAll();
        assertThat(collectionList).hasSize(databaseSizeBeforeCreate + 1);
        Collection testCollection = collectionList.get(collectionList.size() - 1);
        assertThat(testCollection.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCollection.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCollection.getImages()).isEqualTo(DEFAULT_IMAGES);
        assertThat(testCollection.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testCollection.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testCollection.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testCollection.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCollectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collectionRepository.findAll().size();

        // Create the Collection with an existing ID
        collection.setId(1L);
        CollectionDTO collectionDTO = collectionMapper.toDto(collection);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollectionMockMvc.perform(post("/api/collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Collection in the database
        List<Collection> collectionList = collectionRepository.findAll();
        assertThat(collectionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = collectionRepository.findAll().size();
        // set the field null
        collection.setName(null);

        // Create the Collection, which fails.
        CollectionDTO collectionDTO = collectionMapper.toDto(collection);

        restCollectionMockMvc.perform(post("/api/collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collectionDTO)))
            .andExpect(status().isBadRequest());

        List<Collection> collectionList = collectionRepository.findAll();
        assertThat(collectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImagesIsRequired() throws Exception {
        int databaseSizeBeforeTest = collectionRepository.findAll().size();
        // set the field null
        collection.setImages(null);

        // Create the Collection, which fails.
        CollectionDTO collectionDTO = collectionMapper.toDto(collection);

        restCollectionMockMvc.perform(post("/api/collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collectionDTO)))
            .andExpect(status().isBadRequest());

        List<Collection> collectionList = collectionRepository.findAll();
        assertThat(collectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCollections() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get all the collectionList
        restCollectionMockMvc.perform(get("/api/collections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collection.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].images").value(hasItem(DEFAULT_IMAGES)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getCollection() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get the collection
        restCollectionMockMvc.perform(get("/api/collections/{id}", collection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(collection.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.images").value(DEFAULT_IMAGES))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingCollection() throws Exception {
        // Get the collection
        restCollectionMockMvc.perform(get("/api/collections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollection() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        int databaseSizeBeforeUpdate = collectionRepository.findAll().size();

        // Update the collection
        Collection updatedCollection = collectionRepository.findById(collection.getId()).get();
        // Disconnect from session so that the updates on updatedCollection are not directly saved in db
        em.detach(updatedCollection);
        updatedCollection
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .images(UPDATED_IMAGES)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .status(UPDATED_STATUS);
        CollectionDTO collectionDTO = collectionMapper.toDto(updatedCollection);

        restCollectionMockMvc.perform(put("/api/collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collectionDTO)))
            .andExpect(status().isOk());

        // Validate the Collection in the database
        List<Collection> collectionList = collectionRepository.findAll();
        assertThat(collectionList).hasSize(databaseSizeBeforeUpdate);
        Collection testCollection = collectionList.get(collectionList.size() - 1);
        assertThat(testCollection.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCollection.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCollection.getImages()).isEqualTo(UPDATED_IMAGES);
        assertThat(testCollection.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCollection.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testCollection.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testCollection.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingCollection() throws Exception {
        int databaseSizeBeforeUpdate = collectionRepository.findAll().size();

        // Create the Collection
        CollectionDTO collectionDTO = collectionMapper.toDto(collection);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollectionMockMvc.perform(put("/api/collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Collection in the database
        List<Collection> collectionList = collectionRepository.findAll();
        assertThat(collectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCollection() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        int databaseSizeBeforeDelete = collectionRepository.findAll().size();

        // Delete the collection
        restCollectionMockMvc.perform(delete("/api/collections/{id}", collection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Collection> collectionList = collectionRepository.findAll();
        assertThat(collectionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Collection.class);
        Collection collection1 = new Collection();
        collection1.setId(1L);
        Collection collection2 = new Collection();
        collection2.setId(collection1.getId());
        assertThat(collection1).isEqualTo(collection2);
        collection2.setId(2L);
        assertThat(collection1).isNotEqualTo(collection2);
        collection1.setId(null);
        assertThat(collection1).isNotEqualTo(collection2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollectionDTO.class);
        CollectionDTO collectionDTO1 = new CollectionDTO();
        collectionDTO1.setId(1L);
        CollectionDTO collectionDTO2 = new CollectionDTO();
        assertThat(collectionDTO1).isNotEqualTo(collectionDTO2);
        collectionDTO2.setId(collectionDTO1.getId());
        assertThat(collectionDTO1).isEqualTo(collectionDTO2);
        collectionDTO2.setId(2L);
        assertThat(collectionDTO1).isNotEqualTo(collectionDTO2);
        collectionDTO1.setId(null);
        assertThat(collectionDTO1).isNotEqualTo(collectionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(collectionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(collectionMapper.fromId(null)).isNull();
    }
}
