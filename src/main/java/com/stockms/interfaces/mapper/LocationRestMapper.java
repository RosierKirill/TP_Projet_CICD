package com.stockms.interfaces.mapper;

import com.stockms.domain.model.Location;
import com.stockms.interfaces.dto.request.CreateLocationRequest;
import com.stockms.interfaces.dto.response.LocationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** Maps between REST DTOs and domain Location model. */
@Mapper(componentModel = "spring")
public interface LocationRestMapper {

    LocationResponse toResponse(Location location);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    Location toDomain(CreateLocationRequest request);
}
