package com.gialinh.shop.web.rest;

import com.gialinh.shop.service.CollectionService;
import com.gialinh.shop.web.rest.errors.BadRequestAlertException;
import com.gialinh.shop.service.dto.CollectionDTO;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.gialinh.shop.domain.Collection}.
 */
@RestController
@RequestMapping("/api")
public class CollectionResource {

    private final Logger log = LoggerFactory.getLogger(CollectionResource.class);

    private static final String ENTITY_NAME = "collection";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollectionService collectionService;

    public CollectionResource(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    /**
     * {@code POST  /collections} : Create a new collection.
     *
     * @param collectionDTO the collectionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collectionDTO, or with status {@code 400 (Bad Request)} if the collection has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/collections")
    public ResponseEntity<CollectionDTO> createCollection(@Valid @RequestBody CollectionDTO collectionDTO) throws URISyntaxException {
        log.debug("REST request to save Collection : {}", collectionDTO);
        if (collectionDTO.getId() != null) {
            throw new BadRequestAlertException("A new collection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CollectionDTO result = collectionService.save(collectionDTO);
        return ResponseEntity.created(new URI("/api/collections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /collections} : Updates an existing collection.
     *
     * @param collectionDTO the collectionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collectionDTO,
     * or with status {@code 400 (Bad Request)} if the collectionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collectionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/collections")
    public ResponseEntity<CollectionDTO> updateCollection(@Valid @RequestBody CollectionDTO collectionDTO) throws URISyntaxException {
        log.debug("REST request to update Collection : {}", collectionDTO);
        if (collectionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CollectionDTO result = collectionService.save(collectionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collectionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /collections} : get all the collections.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of collections in body.
     */
    @GetMapping("/collections")
    public ResponseEntity<List<CollectionDTO>> getAllCollections(Pageable pageable) {
        log.debug("REST request to get a page of Collections");
        Page<CollectionDTO> page = collectionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /collections/:id} : get the "id" collection.
     *
     * @param id the id of the collectionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collectionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/collections/{id}")
    public ResponseEntity<CollectionDTO> getCollection(@PathVariable Long id) {
        log.debug("REST request to get Collection : {}", id);
        Optional<CollectionDTO> collectionDTO = collectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(collectionDTO);
    }

    /**
     * {@code DELETE  /collections/:id} : delete the "id" collection.
     *
     * @param id the id of the collectionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/collections/{id}")
    public ResponseEntity<Void> deleteCollection(@PathVariable Long id) {
        log.debug("REST request to delete Collection : {}", id);
        collectionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
