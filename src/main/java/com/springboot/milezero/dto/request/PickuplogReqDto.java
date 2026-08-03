package com.springboot.milezero.dto.request;

import com.springboot.milezero.enums.FuelLevel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;


public record PickuplogReqDto(
        @NotNull(message = "Actual pickup date can't be empty")
        Instant actualPickup,

        @Positive(message = "Odometer can't be empty")
        long odometer,

        FuelLevel fuelLevel,

        String remarks,

        @NotNull(message = "Vehicle ID can't be empty")
        long vehicleId,

        @NotNull(message = "Booking ID can't be empty")
        long bookingId,

        @NotNull(message = "Employee ID can't be empty")
        long employeeId
) { }
