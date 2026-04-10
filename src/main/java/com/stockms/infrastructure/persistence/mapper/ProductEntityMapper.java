package com.stockms.infrastructure.persistence.mapper;

import com.stockms.domain.model.Product;
import com.stockms.infrastructure.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper between JPA entity and domain model.
 * Design pattern: Repository — translates between persistence and domain representations.
 */
@Mapper(componentModel = "spring")
public interface ProductEntityMapper {

    Product toDomain(ProductEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProductEntity toEntity(Product domain);
}
