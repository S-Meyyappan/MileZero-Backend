package com.springboot.milezero.dto.response;

import com.springboot.milezero.enums.FuelLevel;

import java.math.BigDecimal;
import java.time.Instant;


public record  ReturnlogRespDto(
        Long id,
        Instant actualReturn,
        Long odometer,
        FuelLevel fuelLevel,
        BigDecimal extraCharges,
        String remarks,
        String employeeName
) {
}
