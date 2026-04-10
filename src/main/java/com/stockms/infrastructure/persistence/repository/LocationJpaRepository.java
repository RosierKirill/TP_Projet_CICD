package com.stockms.infrastructure.persistence.repository;

import com.stockms.infrastructure.persistence.entity.LocationEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Spring Data JPA repository for locations. */
public interface LocationJpaRepository extends JpaRepository<LocationEntity, UUID> {
    boolean existsByStoreCodeAndName(String storeCode, String name);
}
