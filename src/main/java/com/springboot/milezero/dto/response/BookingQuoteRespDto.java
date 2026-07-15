package com.springboot.milezero.dto.response;

import java.math.BigDecimal;

public record BookingQuoteRespDto(
        BigDecimal baseCost,
        BigDecimal addonCost,
        BigDecimal totalEstimateCost,
        Integer totalIncludedKm
) {
}
