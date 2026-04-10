package com.stockms.domain.model;

import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StockMovementTest {

    @Test
    void allArgsConstructorAndGetters() {
        UUID id = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID fromLoc = UUID.randomUUID();
        UUID toLoc = UUID.randomUUID();
        Instant now = Instant.now();

        StockMovement m = new StockMovement(
                id, MovementType.TRANSFER, productId,
                fromLoc, toLoc, 10, "restock", "SUP-001",
                now, "employee1");

        assertEquals(id, m.getId());
        assertEquals(MovementType.TRANSFER, m.getType());
        assertEquals(productId, m.getProductId());
        assertEquals(fromLoc, m.getFromLocationId());
        assertEquals(toLoc, m.getToLocationId());
        assertEquals(10, m.getQuantity());
        assertEquals("restock", m.getReason());
        assertEquals("SUP-001", m.getSupplierRef());
        assertEquals(now, m.getCreatedAt());
        assertEquals("employee1", m.getCreatedBy());
    }

    @Test
    void noArgsConstructorFieldsAreNull() {
        StockMovement m = new StockMovement();
        assertNull(m.getId());
        assertNull(m.getType());
        assertNull(m.getProductId());
    }

    @Test
    void settersUpdateFields() {
        StockMovement m = new StockMovement();
        UUID id = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        m.setId(id);
        m.setType(MovementType.IN);
        m.setProductId(productId);
        m.setFromLocationId(null);
        m.setToLocationId(UUID.randomUUID());
        m.setQuantity(5);
        m.setReason("delivery");
        m.setSupplierRef("SUP-002");
        m.setCreatedAt(Instant.now());
        m.setCreatedBy("manager1");

        assertEquals(id, m.getId());
        assertEquals(MovementType.IN, m.getType());
        assertEquals(productId, m.getProductId());
        assertNull(m.getFromLocationId());
    }

    @Test
    void movementTypeValues() {
        assertEquals(4, MovementType.values().length);
        assertEquals(MovementType.IN, MovementType.valueOf("IN"));
        assertEquals(MovementType.OUT, MovementType.valueOf("OUT"));
        assertEquals(MovementType.TRANSFER, MovementType.valueOf("TRANSFER"));
        assertEquals(MovementType.ADJUSTMENT, MovementType.valueOf("ADJUSTMENT"));
    }
}
