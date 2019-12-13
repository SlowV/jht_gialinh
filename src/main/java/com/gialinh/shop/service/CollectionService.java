package com.gialinh.shop.service;

import com.gialinh.shop.service.dto.CollectionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.gialinh.shop.domain.Collection}.
 */
public interface CollectionService {

    /**
     * Save a collection.
     *
     * @param collectionDTO the entity to save.
     * @return the persisted entity.
     */
    CollectionDTO save(CollectionDTO collectionDTO);

    /**
     * Get all the collections.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CollectionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" collection.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CollectionDTO> findOne(Long id);

    /**
     * Delete the "id" collection.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
