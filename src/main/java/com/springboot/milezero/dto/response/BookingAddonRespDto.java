package com.springboot.milezero.dto.response;

import java.math.BigDecimal;

public record BookingAddonRespDto(
        String name,
        BigDecimal pricePerDay,
        int quantity
) {
}
