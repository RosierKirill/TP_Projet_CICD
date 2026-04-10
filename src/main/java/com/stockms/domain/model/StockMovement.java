package com.stockms.domain.model;

import java.time.Instant;
import java.util.UUID;

/** Immutable record of a stock movement. */
public class StockMovement {

    private UUID id;
    private MovementType type;
    private UUID productId;
    private UUID fromLocationId;
    private UUID toLocationId;
    private int quantity;
    private String reason;
    private String supplierRef;
    private Instant createdAt;
    private String createdBy;

    public StockMovement() {
    }

    @SuppressWarnings("checkstyle:ParameterNumber")
    public StockMovement(UUID id, MovementType type, UUID productId,
                         UUID fromLocationId, UUID toLocationId,
                         int quantity, String reason, String supplierRef,
                         Instant createdAt, String createdBy) {
        this.id = id;
        this.type = type;
        this.productId = productId;
        this.fromLocationId = fromLocationId;
        this.toLocationId = toLocationId;
        this.quantity = quantity;
        this.reason = reason;
        this.supplierRef = supplierRef;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public MovementType getType() {
        return type;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public UUID getFromLocationId() {
        return fromLocationId;
    }

    public void setFromLocationId(UUID fromLocationId) {
        this.fromLocationId = fromLocationId;
    }

    public UUID getToLocationId() {
        return toLocationId;
    }

    public void setToLocationId(UUID toLocationId) {
        this.toLocationId = toLocationId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSupplierRef() {
        return supplierRef;
    }

    public void setSupplierRef(String supplierRef) {
        this.supplierRef = supplierRef;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
