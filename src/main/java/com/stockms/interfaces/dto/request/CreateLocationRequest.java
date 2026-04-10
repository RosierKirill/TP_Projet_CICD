package com.stockms.interfaces.dto.request;

import com.stockms.domain.model.LocationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** Request body for location creation. */
public record CreateLocationRequest(

        @NotBlank(message = "Store code is required")
        @Size(max = 20)
        String storeCode,

        @NotBlank(message = "Name is required")
        @Size(max = 255)
        String name,

        @NotNull(message = "Location type is required (RESERVE or AISLE)")
        LocationType type
) {
}
