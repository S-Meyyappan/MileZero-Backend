package com.springboot.milezero.dto.response;

import com.springboot.milezero.enums.FuelLevel;

import java.time.Instant;


public record PickuplogRespDto(
        Long id,
        Instant actualPickup,
        Long odometer,
        FuelLevel fuelLevel,
        String remarks,
        String employeeName
) { }
