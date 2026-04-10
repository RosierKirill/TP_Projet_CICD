package com.stockms.domain.event;

import com.stockms.domain.model.StockMovement;

/**
 * Domain event published after a StockMovement is persisted.
 * Listeners: stock-update (updates StockItem quantities) and audit-log.
 * Design pattern: Observer — decouples movement recording from side-effects.
 */
public class StockMovementRecordedEvent {

    private final StockMovement movement;

    public StockMovementRecordedEvent(StockMovement movement) {
        this.movement = movement;
    }

    public StockMovement getMovement() {
        return movement;
    }
}
