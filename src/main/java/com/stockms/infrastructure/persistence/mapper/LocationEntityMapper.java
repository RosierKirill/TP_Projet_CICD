package com.stockms.infrastructure.persistence.mapper;

import com.stockms.domain.model.Location;
import com.stockms.infrastructure.persistence.entity.LocationEntity;
import org.mapstruct.Mapper;

/** MapStruct mapper between LocationEntity and Location domain model. */
@Mapper(componentModel = "spring")
public interface LocationEntityMapper {

    Location toDomain(LocationEntity entity);

    LocationEntity toEntity(Location domain);
}
