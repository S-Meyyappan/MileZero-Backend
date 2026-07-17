package com.springboot.milezero.dto.request;

import com.springboot.milezero.enums.FuelLevel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

public record ReturnlogReqDto(
        @NotNull(message = "Actual return date can't be empty")
        LocalDateTime actualReturn,

        @PositiveOrZero(message = "Odometer can't be empty")
        int odometer,

        FuelLevel fuelLevel,

        String remarks,

        @NotNull(message = "Booking ID can't be empty")
        long bookingId,

        @NotNull(message = "Booking ID can't be empty")
        long employeeId
) {
}
