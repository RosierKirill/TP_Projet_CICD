package com.stockms.infrastructure.persistence.adapter;

import com.stockms.domain.model.Location;
import com.stockms.domain.port.out.LocationRepository;
import com.stockms.infrastructure.persistence.entity.LocationEntity;
import com.stockms.infrastructure.persistence.mapper.LocationEntityMapper;
import com.stockms.infrastructure.persistence.repository.LocationJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

/** Adapter implementing the domain's LocationRepository output port. */
@Component
public class LocationRepositoryAdapter implements LocationRepository {

    private final LocationJpaRepository jpaRepository;
    private final LocationEntityMapper mapper;

    public LocationRepositoryAdapter(LocationJpaRepository jpaRepository,
                                     LocationEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Location save(Location location) {
        LocationEntity entity = mapper.toEntity(location);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Location> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Location> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByStoreCodeAndName(String storeCode, String name) {
        return jpaRepository.existsByStoreCodeAndName(storeCode, name);
    }
}
