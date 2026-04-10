package com.stockms.domain.model;

import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductTest {

    @Test
    void allArgsConstructorAndGetters() {
        UUID id = UUID.randomUUID();
        Product p = new Product(id, "3256910000000", "Milk 1L",
                "Dairy", "L", new BigDecimal("5.50"), true);

        assertEquals(id, p.getId());
        assertEquals("3256910000000", p.getEan());
        assertEquals("Milk 1L", p.getName());
        assertEquals("Dairy", p.getCategory());
        assertEquals("L", p.getUnit());
        assertEquals(new BigDecimal("5.50"), p.getVatRate());
        assertTrue(p.isActive());
    }

    @Test
    void noArgsConstructorDefaultsAreNull() {
        Product p = new Product();
        assertNull(p.getId());
        assertNull(p.getEan());
        assertNull(p.getName());
    }

    @Test
    void settersUpdateFields() {
        Product p = new Product();
        UUID id = UUID.randomUUID();

        p.setId(id);
        p.setEan("0000000000001");
        p.setName("Butter");
        p.setCategory("Dairy");
        p.setUnit("KG");
        p.setVatRate(new BigDecimal("5.50"));
        p.setActive(false);

        assertEquals(id, p.getId());
        assertEquals("0000000000001", p.getEan());
        assertEquals("Butter", p.getName());
        assertEquals("Dairy", p.getCategory());
        assertEquals("KG", p.getUnit());
        assertEquals(new BigDecimal("5.50"), p.getVatRate());
        assertFalse(p.isActive());
    }
}
