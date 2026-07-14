package com.springboot.milezero.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AddonReqDto(
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotNull(message = "Price cannot be null")
        BigDecimal pricePerDay
) {
}
