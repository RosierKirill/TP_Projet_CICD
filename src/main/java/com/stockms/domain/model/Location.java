package com.stockms.domain.model;

import java.util.UUID;

/** Physical location (reserve shelf, aisle slot) in a store. */
public class Location {

    private UUID id;
    private String storeCode;
    private String name;
    private LocationType type;
    private boolean active;

    public Location() {
    }

    public Location(UUID id, String storeCode, String name, LocationType type, boolean active) {
        this.id = id;
        this.storeCode = storeCode;
        this.name = name;
        this.type = type;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationType getType() {
        return type;
    }

    public void setType(LocationType type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
