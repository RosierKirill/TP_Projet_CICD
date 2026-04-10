package com.stockms.domain.port.out;

import com.stockms.domain.model.Product;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Output port (driven side) — persistence contract for Product. */
public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(UUID id);
    Optional<Product> findByEan(String ean);
    List<Product> findAll();
    boolean existsByEan(String ean);
}
