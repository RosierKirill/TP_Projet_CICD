package com.stockms.domain.model;

import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StockItemTest {

    @Test
    void allArgsConstructorAndGetters() {
        UUID id = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID locationId = UUID.randomUUID();

        StockItem item = new StockItem(id, productId, locationId, 42);

        assertEquals(id, item.getId());
        assertEquals(productId, item.getProductId());
        assertEquals(locationId, item.getLocationId());
        assertEquals(42, item.getQuantity());
    }

    @Test
    void settersUpdateFields() {
        StockItem item = new StockItem();
        UUID id = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID locationId = UUID.randomUUID();

        item.setId(id);
        item.setProductId(productId);
        item.setLocationId(locationId);
        item.setQuantity(100);

        assertEquals(id, item.getId());
        assertEquals(productId, item.getProductId());
        assertEquals(locationId, item.getLocationId());
        assertEquals(100, item.getQuantity());
    }
}
