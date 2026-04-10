package com.stockms.application.service;

import com.stockms.domain.exception.ConflictException;
import com.stockms.domain.exception.ResourceNotFoundException;
import com.stockms.domain.model.Product;
import com.stockms.domain.port.out.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product aProduct;

    @BeforeEach
    void setUp() {
        aProduct = new Product(UUID.randomUUID(), "3256910000001", "Milk 1L",
                "Dairy", "L", new BigDecimal("5.50"), true);
    }

    @Test
    void create_newEan_savesAndReturns() {
        given(productRepository.existsByEan("3256910000001")).willReturn(false);
        given(productRepository.save(any(Product.class))).willReturn(aProduct);

        Product result = productService.create(aProduct);

        assertThat(result.getEan()).isEqualTo("3256910000001");
        assertThat(result.isActive()).isTrue();
        then(productRepository).should().save(aProduct);
    }

    @Test
    void create_duplicateEan_throwsConflict() {
        given(productRepository.existsByEan("3256910000001")).willReturn(true);

        assertThatThrownBy(() -> productService.create(aProduct))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("3256910000001");

        then(productRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void findById_exists_returnsProduct() {
        given(productRepository.findById(aProduct.getId()))
                .willReturn(Optional.of(aProduct));

        Product result = productService.findById(aProduct.getId());

        assertThat(result).isEqualTo(aProduct);
    }

    @Test
    void findById_notFound_throwsResourceNotFound() {
        UUID unknown = UUID.randomUUID();
        given(productRepository.findById(unknown)).willReturn(Optional.empty());

        assertThatThrownBy(() -> productService.findById(unknown))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(unknown.toString());
    }

    @Test
    void findAll_returnsList() {
        given(productRepository.findAll()).willReturn(List.of(aProduct));

        List<Product> result = productService.findAll();

        assertThat(result).hasSize(1).contains(aProduct);
    }

    @Test
    void update_exists_updatesFields() {
        given(productRepository.findById(aProduct.getId()))
                .willReturn(Optional.of(aProduct));
        given(productRepository.save(aProduct)).willReturn(aProduct);

        Product result = productService.update(aProduct.getId(),
                "New Name", "NewCat", "KG", new BigDecimal("10.00"));

        assertThat(result.getName()).isEqualTo("New Name");
        assertThat(result.getCategory()).isEqualTo("NewCat");
        assertThat(result.getVatRate()).isEqualByComparingTo("10.00");
    }

    @Test
    void update_notFound_throwsResourceNotFound() {
        UUID unknown = UUID.randomUUID();
        given(productRepository.findById(unknown)).willReturn(Optional.empty());

        assertThatThrownBy(() -> productService.update(unknown, "N", "C", "U", BigDecimal.ONE))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deactivate_exists_setsActiveFalse() {
        given(productRepository.findById(aProduct.getId()))
                .willReturn(Optional.of(aProduct));
        given(productRepository.save(aProduct)).willReturn(aProduct);

        productService.deactivate(aProduct.getId());

        assertThat(aProduct.isActive()).isFalse();
        then(productRepository).should().save(aProduct);
    }

    @Test
    void deactivate_notFound_throwsResourceNotFound() {
        UUID unknown = UUID.randomUUID();
        given(productRepository.findById(unknown)).willReturn(Optional.empty());

        assertThatThrownBy(() -> productService.deactivate(unknown))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
