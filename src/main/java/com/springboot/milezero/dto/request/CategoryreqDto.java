package com.springboot.milezero.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CategoryReqDto(
        @NotBlank(message = "Field cannot be empty")
        String name,
        @NotNull(message = "Field cannot be empty")
        BigDecimal basePricePerDay,
        @NotNull(message = "Field cannot be empty")
        BigDecimal basePricePerHour,
        @NotNull(message = "Field cannot be empty")
        BigDecimal basePricePerKm,
        @Positive(message = "Field cannot be empty")
        int includedKmPerDay
) {
}
