package com.stockms.interfaces.rest;

import com.stockms.interfaces.dto.request.CreateProductRequest;
import com.stockms.interfaces.dto.request.UpdateProductRequest;
import com.stockms.interfaces.dto.response.ProductResponse;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

class ProductControllerIT extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private JdbcTemplate jdbc;

    @BeforeEach
    void cleanDb() {
        jdbc.execute("DELETE FROM stock_movement");
        jdbc.execute("DELETE FROM stock_item");
        jdbc.execute("DELETE FROM product");
    }

    @Test
    void createProduct_validRequest_returns201() {
        CreateProductRequest req = new CreateProductRequest(
                "3256910000001", "Milk 1L", "Dairy", "L", new BigDecimal("5.50"));

        ResponseEntity<ProductResponse> res = rest.postForEntity(
                "/api/v1/products", req, ProductResponse.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(res.getBody()).isNotNull();
        assertThat(res.getBody().ean()).isEqualTo("3256910000001");
        assertThat(res.getBody().active()).isTrue();
        assertThat(res.getBody().id()).isNotNull();
    }

    @Test
    void createProduct_blankEan_returns400() {
        CreateProductRequest req = new CreateProductRequest(
                "", "Milk", "Dairy", "L", new BigDecimal("5.50"));

        ResponseEntity<String> res = rest.postForEntity(
                "/api/v1/products", req, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void createProduct_invalidEanFormat_returns400() {
        CreateProductRequest req = new CreateProductRequest(
                "NOT-AN-EAN", "Milk", "Dairy", "L", new BigDecimal("5.50"));

        ResponseEntity<String> res = rest.postForEntity(
                "/api/v1/products", req, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void createProduct_duplicateEan_returns409() {
        CreateProductRequest req = new CreateProductRequest(
                "3256910000002", "Butter", "Dairy", "KG", new BigDecimal("5.50"));
        rest.postForEntity("/api/v1/products", req, ProductResponse.class);

        ResponseEntity<String> second = rest.postForEntity(
                "/api/v1/products", req, String.class);

        assertThat(second.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void getProductById_exists_returns200() {
        CreateProductRequest req = new CreateProductRequest(
                "3256910000003", "Eggs 6pk", "Produce", "PACK", new BigDecimal("0.00"));
        ProductResponse created = rest.postForEntity(
                "/api/v1/products", req, ProductResponse.class).getBody();

        assertThat(created).isNotNull();

        ResponseEntity<ProductResponse> res = rest.getForEntity(
                "/api/v1/products/" + created.id(), ProductResponse.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getBody()).isNotNull();
        assertThat(res.getBody().ean()).isEqualTo("3256910000003");
    }

    @Test
    void getProductById_notFound_returns404() {
        ResponseEntity<String> res = rest.getForEntity(
                "/api/v1/products/" + UUID.randomUUID(), String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void listProducts_emptyThenOne_returnsCorrectCount() {
        ResponseEntity<ProductResponse[]> empty = rest.getForEntity(
                "/api/v1/products", ProductResponse[].class);
        assertThat(empty.getBody()).isEmpty();

        rest.postForEntity("/api/v1/products",
                new CreateProductRequest("3256910000004", "Yogurt", "Dairy", "G",
                        new BigDecimal("5.50")),
                ProductResponse.class);

        ResponseEntity<ProductResponse[]> one = rest.getForEntity(
                "/api/v1/products", ProductResponse[].class);
        assertThat(one.getBody()).hasSize(1);
    }

    @Test
    void updateProduct_validRequest_returns200WithUpdatedFields() {
        ProductResponse created = rest.postForEntity("/api/v1/products",
                new CreateProductRequest("3256910000005", "Old Name", "Cat", "U",
                        new BigDecimal("5.50")),
                ProductResponse.class).getBody();

        assertThat(created).isNotNull();

        UpdateProductRequest update = new UpdateProductRequest(
                "New Name", "NewCat", "KG", new BigDecimal("10.00"));

        ResponseEntity<ProductResponse> res = rest.exchange(
                "/api/v1/products/" + created.id(),
                HttpMethod.PUT,
                new HttpEntity<>(update),
                ProductResponse.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getBody()).isNotNull();
        assertThat(res.getBody().name()).isEqualTo("New Name");
        assertThat(res.getBody().ean()).isEqualTo("3256910000005");
    }

    @Test
    void deactivateProduct_returns204_thenStillListedAsInactive() {
        ProductResponse created = rest.postForEntity("/api/v1/products",
                new CreateProductRequest("3256910000006", "Cheese", "Dairy", "G",
                        new BigDecimal("5.50")),
                ProductResponse.class).getBody();

        assertThat(created).isNotNull();

        ResponseEntity<Void> del = rest.exchange(
                "/api/v1/products/" + created.id(),
                HttpMethod.DELETE, null, Void.class);

        assertThat(del.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<ProductResponse> fetched = rest.getForEntity(
                "/api/v1/products/" + created.id(), ProductResponse.class);
        assertThat(fetched.getBody()).isNotNull();
        assertThat(fetched.getBody().active()).isFalse();
    }
}
