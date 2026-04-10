package com.stockms.domain.model;

import java.util.UUID;

/** Current stock level for a product at a specific location. */
public class StockItem {

    private UUID id;
    private UUID productId;
    private UUID locationId;
    private int quantity;

    public StockItem() {
    }

    public StockItem(UUID id, UUID productId, UUID locationId, int quantity) {
        this.id = id;
        this.productId = productId;
        this.locationId = locationId;
        this.quantity = quantity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public UUID getLocationId() {
        return locationId;
    }

    public void setLocationId(UUID locationId) {
        this.locationId = locationId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
