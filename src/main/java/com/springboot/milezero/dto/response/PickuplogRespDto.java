package com.springboot.milezero.dto.response;

import com.springboot.milezero.enums.FuelLevel;

import java.time.LocalDateTime;

public record PickuplogRespDto(
        Long id,
        LocalDateTime actualPickup,
        Long odometer,
        FuelLevel fuelLevel,
        String remarks,
        String employeeName
) { }
