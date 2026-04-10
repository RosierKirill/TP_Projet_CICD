package com.stockms.domain.event;

import com.stockms.domain.model.MovementType;
import com.stockms.domain.model.StockMovement;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class StockMovementRecordedEventTest {

    @Test
    void eventWrapsMovement() {
        StockMovement movement = new StockMovement(
                UUID.randomUUID(), MovementType.IN, UUID.randomUUID(),
                null, UUID.randomUUID(), 3, null, null,
                Instant.now(), "user1");

        StockMovementRecordedEvent event = new StockMovementRecordedEvent(movement);

        assertSame(movement, event.getMovement());
        assertEquals(MovementType.IN, event.getMovement().getType());
    }
}
