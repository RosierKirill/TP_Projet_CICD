package com.stockms.interfaces.rest;

import com.stockms.application.service.ProductService;
import com.stockms.interfaces.dto.request.CreateProductRequest;
import com.stockms.interfaces.dto.request.UpdateProductRequest;
import com.stockms.interfaces.dto.response.ProductResponse;
import com.stockms.interfaces.mapper.ProductRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for product CRUD. No business logic — delegates to ProductService. */
@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products", description = "Product catalogue management")
public class ProductController {

    private final ProductService productService;
    private final ProductRestMapper mapper;

    public ProductController(ProductService productService, ProductRestMapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create a new product")
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody CreateProductRequest request) {
        ProductResponse body = mapper.toResponse(
                productService.create(mapper.toDomain(request)));
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @GetMapping
    @Operation(summary = "List all products")
    public ResponseEntity<List<ProductResponse>> findAll() {
        List<ProductResponse> body = productService.findAll().stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by ID")
    public ResponseEntity<ProductResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(mapper.toResponse(productService.findById(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product (EAN is immutable)")
    public ResponseEntity<ProductResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateProductRequest request) {
        ProductResponse body = mapper.toResponse(
                productService.update(id, request.name(), request.category(),
                        request.unit(), request.vatRate()));
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deactivate a product (soft delete)")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        productService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
