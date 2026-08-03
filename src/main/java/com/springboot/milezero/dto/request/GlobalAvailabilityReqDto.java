package com.springboot.milezero.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;


public record GlobalAvailabilityReqDto(
        @NotNull(message = "Field cannot be null")
        Instant chosenPickup,

        @NotNull(message = "Field cannot be null")
        Instant chosenReturn
) {
}
