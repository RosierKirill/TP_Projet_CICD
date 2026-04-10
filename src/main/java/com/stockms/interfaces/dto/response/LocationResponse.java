package com.stockms.interfaces.dto.response;

import com.stockms.domain.model.LocationType;
import java.util.UUID;

/** Location representation returned by the API. */
public record LocationResponse(
        UUID id,
        String storeCode,
        String name,
        LocationType type,
        boolean active
) {
}
