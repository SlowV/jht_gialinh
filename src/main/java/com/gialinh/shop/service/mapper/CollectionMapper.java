package com.gialinh.shop.service.mapper;

import com.gialinh.shop.domain.*;
import com.gialinh.shop.service.dto.CollectionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Collection} and its DTO {@link CollectionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CollectionMapper extends EntityMapper<CollectionDTO, Collection> {


    @Mapping(target = "products", ignore = true)
    @Mapping(target = "removeProduct", ignore = true)
    Collection toEntity(CollectionDTO collectionDTO);

    default Collection fromId(Long id) {
        if (id == null) {
            return null;
        }
        Collection collection = new Collection();
        collection.setId(id);
        return collection;
    }
}
