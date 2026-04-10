package com.stockms.interfaces.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

/** Product representation returned by the API. */
public record ProductResponse(
        UUID id,
        String ean,
        String name,
        String category,
        String unit,
        BigDecimal vatRate,
        boolean active
) {
}
