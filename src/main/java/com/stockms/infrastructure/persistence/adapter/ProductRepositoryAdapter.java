package com.stockms.infrastructure.persistence.adapter;

import com.stockms.domain.model.Product;
import com.stockms.domain.port.out.ProductRepository;
import com.stockms.infrastructure.persistence.entity.ProductEntity;
import com.stockms.infrastructure.persistence.mapper.ProductEntityMapper;
import com.stockms.infrastructure.persistence.repository.ProductJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 * Adapter (infrastructure) implementing the domain's ProductRepository output port.
 * Design pattern: Repository — isolates domain from JPA details.
 */
@Component
public class ProductRepositoryAdapter implements ProductRepository {

    private final ProductJpaRepository jpaRepository;
    private final ProductEntityMapper mapper;

    public ProductRepositoryAdapter(ProductJpaRepository jpaRepository,
                                    ProductEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = mapper.toEntity(product);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Product> findByEan(String ean) {
        return jpaRepository.findByEan(ean).map(mapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByEan(String ean) {
        return jpaRepository.existsByEan(ean);
    }
}
