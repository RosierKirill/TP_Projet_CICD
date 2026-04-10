package com.stockms.domain.model;

import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocationTest {

    @Test
    void allArgsConstructorAndGetters() {
        UUID id = UUID.randomUUID();
        Location l = new Location(id, "STORE01", "Aisle-A1", LocationType.AISLE, true);

        assertEquals(id, l.getId());
        assertEquals("STORE01", l.getStoreCode());
        assertEquals("Aisle-A1", l.getName());
        assertEquals(LocationType.AISLE, l.getType());
        assertTrue(l.isActive());
    }

    @Test
    void settersUpdateFields() {
        Location l = new Location();
        UUID id = UUID.randomUUID();

        l.setId(id);
        l.setStoreCode("STORE02");
        l.setName("Reserve-R1");
        l.setType(LocationType.RESERVE);
        l.setActive(false);

        assertEquals(id, l.getId());
        assertEquals("STORE02", l.getStoreCode());
        assertEquals("Reserve-R1", l.getName());
        assertEquals(LocationType.RESERVE, l.getType());
    }

    @Test
    void locationTypeValues() {
        assertEquals(2, LocationType.values().length);
        assertEquals(LocationType.RESERVE, LocationType.valueOf("RESERVE"));
        assertEquals(LocationType.AISLE, LocationType.valueOf("AISLE"));
    }
}
