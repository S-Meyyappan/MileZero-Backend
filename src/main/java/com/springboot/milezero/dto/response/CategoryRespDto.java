package com.springboot.milezero.dto.response;

import java.math.BigDecimal;

public record CategoryRespDto(
        String categoryName,
        BigDecimal basePricePerDay,
        BigDecimal basePricePerHour,
        BigDecimal basePricePerKm,
        int includedKmPerDay
) {
}
