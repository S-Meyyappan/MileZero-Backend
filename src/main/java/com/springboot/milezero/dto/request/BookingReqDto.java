package com.springboot.milezero.dto.request;

import com.springboot.milezero.enums.BookingStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record BookingReqDto(
        @NotNull(message = "Planned pickup date can't be empty")
        LocalDateTime plannedPickup,

        @NotNull(message = "Planned return date can't be empty")
        LocalDateTime plannedReturn,

        @PositiveOrZero(message = "Included kilometers can't be negative")
        int includedKm,

        @NotNull(message = "Booked rate can't be empty")
        BigDecimal bookedRate,

        @NotNull(message = "Estimated cost can't be empty")
        BigDecimal estimatedCost,

        @NotNull(message = "Final cost can't be empty")
        BigDecimal finalCost,

        @NotNull(message = "Booking status can't be empty")
        BookingStatus bookingStatus,

        @NotNull(message = "Customer ID can't be empty")
        Long customerId,

        @NotNull(message = "Category ID can't be empty")
        Long categoryId,

        @NotNull(message = "Pickup branch ID can't be empty")
        Long pickupBranchId,

        @NotNull(message = "Return branch ID can't be empty")
        Long returnBranchId,

        List<BookingAddonDto> bookingAddons //List of Addon ids
) {
}
