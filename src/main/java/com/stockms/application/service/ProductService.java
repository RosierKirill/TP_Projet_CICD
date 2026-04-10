package com.stockms.application.service;

import com.stockms.domain.exception.ConflictException;
import com.stockms.domain.exception.ResourceNotFoundException;
import com.stockms.domain.model.Product;
import com.stockms.domain.port.out.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service orchestrating product use cases.
 * Depends only on domain ports — no JPA or framework concerns here.
 */
@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(Product product) {
        if (productRepository.existsByEan(product.getEan())) {
            throw new ConflictException("Product with EAN already exists: " + product.getEan());
        }
        product.setActive(true);
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Product findById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product update(UUID id, String name, String category,
                          String unit, BigDecimal vatRate) {
        Product existing = findById(id);
        existing.setName(name);
        existing.setCategory(category);
        existing.setUnit(unit);
        existing.setVatRate(vatRate);
        return productRepository.save(existing);
    }

    public void deactivate(UUID id) {
        Product existing = findById(id);
        existing.setActive(false);
        productRepository.save(existing);
    }
}
