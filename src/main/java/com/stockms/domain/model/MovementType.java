package com.stockms.domain.model;

/** Stock movement types. Used by the Strategy pattern to select the correct update algorithm. */
public enum MovementType {
    /** Goods received from supplier — increases stock at destination location. */
    IN,
    /** Goods removed (sale, waste, loss) — decreases stock at source location. */
    OUT,
    /** Internal transfer between two locations — decrease source, increase destination. */
    TRANSFER,
    /** Manual inventory correction — sets quantity to the counted value. */
    ADJUSTMENT
}
