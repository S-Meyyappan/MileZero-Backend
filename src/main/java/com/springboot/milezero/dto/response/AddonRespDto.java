package com.springboot.milezero.dto.response;

import java.math.BigDecimal;

public record AddonRespDto(
        Long id,
        String name,
        BigDecimal pricePerDay
) {
}
