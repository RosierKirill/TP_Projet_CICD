package com.stockms.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

/** Pure domain object — no JPA or framework annotations. */
public class Product {

    private UUID id;
    private String ean;
    private String name;
    private String category;
    private String unit;
    private BigDecimal vatRate;
    private boolean active;

    public Product() {
    }

    public Product(UUID id, String ean, String name, String category,
                   String unit, BigDecimal vatRate, boolean active) {
        this.id = id;
        this.ean = ean;
        this.name = name;
        this.category = category;
        this.unit = unit;
        this.vatRate = vatRate;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
