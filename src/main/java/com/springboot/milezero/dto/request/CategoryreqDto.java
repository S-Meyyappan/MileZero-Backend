package com.springboot.milezero.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CategoryreqDto(
        @NotBlank(message = "Field cannot be empty")
        String name,
        @NotNull(message = "Field cannot be empty")
        BigDecimal basePricePerDay,
        @NotNull(message = "Field cannot be empty")
        BigDecimal lateFeePerHour,
        @NotNull(message = "Field cannot be empty")
        BigDecimal extraFeePerKm
) {
}
