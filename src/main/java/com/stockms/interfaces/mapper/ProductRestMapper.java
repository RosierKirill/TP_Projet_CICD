package com.stockms.interfaces.mapper;

import com.stockms.domain.model.Product;
import com.stockms.interfaces.dto.request.CreateProductRequest;
import com.stockms.interfaces.dto.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** Maps between REST DTOs and domain Product model. */
@Mapper(componentModel = "spring")
public interface ProductRestMapper {

    ProductResponse toResponse(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    Product toDomain(CreateProductRequest request);
}
