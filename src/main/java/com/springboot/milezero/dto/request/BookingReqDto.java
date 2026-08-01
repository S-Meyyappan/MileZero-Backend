package com.springboot.milezero.dto.request;

import com.springboot.milezero.enums.BookingType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;
import java.util.List;

public record BookingReqDto(
        @NotNull(message = "Planned pickup date can't be empty")
        LocalDateTime plannedPickup,

        @NotNull(message = "Planned return date can't be empty")
        LocalDateTime plannedReturn,

        @PositiveOrZero(message = "Included kilometers can't be negative")
        int requestedKm,

        @NotNull(message = "Customer ID can't be empty")
        Long customerId,

        @NotNull(message = "Vehicle ID can't be empty")
        Long vehicleId,

        @NotNull(message = "Pickup branch ID can't be empty")
        Long pickupBranchId,

        @NotNull(message = "Return branch ID can't be empty")
        Long returnBranchId,

        BookingType bookingType,

        List<BookingAddonDto> bookingAddons //List of Addon ids
) {
}
