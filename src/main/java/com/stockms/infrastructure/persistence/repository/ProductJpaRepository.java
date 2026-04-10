package com.stockms.infrastructure.persistence.repository;

import com.stockms.infrastructure.persistence.entity.ProductEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Spring Data JPA repository — infrastructure detail, not visible to domain/application. */
public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {
    Optional<ProductEntity> findByEan(String ean);
    boolean existsByEan(String ean);
}
