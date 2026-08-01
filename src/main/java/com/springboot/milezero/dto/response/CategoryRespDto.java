package com.springboot.milezero.dto.response;

import java.math.BigDecimal;

public record CategoryRespDto(
        Long id,
        String name,
        BigDecimal basePricePerDay,
        BigDecimal basePricePerHour,
        BigDecimal basePricePerKm,
        int includedKmPerDay
) {
}
