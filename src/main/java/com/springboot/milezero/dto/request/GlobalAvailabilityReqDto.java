package com.springboot.milezero.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record GlobalAvailabilityReqDto(
        @NotNull(message = "Field cannot be null")
        LocalDateTime chosenPickup,

        @NotNull(message = "Field cannot be null")
        LocalDateTime chosenReturn
) {
}
