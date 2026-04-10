package com.stockms.interfaces.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/** Request body for product creation. */
public record CreateProductRequest(

        @NotBlank(message = "EAN is required")
        @Pattern(regexp = "\\d{8,13}", message = "EAN must be 8 to 13 digits")
        String ean,

        @NotBlank(message = "Name is required")
        @Size(max = 255)
        String name,

        @Size(max = 100)
        String category,

        @NotBlank(message = "Unit is required")
        @Size(max = 20)
        String unit,

        @NotNull(message = "VAT rate is required")
        @DecimalMin(value = "0.0", message = "VAT rate must be >= 0")
        @DecimalMax(value = "100.0", message = "VAT rate must be <= 100")
        BigDecimal vatRate
) {
}
