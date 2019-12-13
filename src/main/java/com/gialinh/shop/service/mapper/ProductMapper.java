package com.gialinh.shop.service.mapper;

import com.gialinh.shop.domain.*;
import com.gialinh.shop.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, CollectionMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "collection.id", target = "collectionId")
    ProductDTO toDto(Product product);

    @Mapping(target = "ordersProducts", ignore = true)
    @Mapping(target = "removeOrdersProduct", ignore = true)
    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "collectionId", target = "collection")
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
