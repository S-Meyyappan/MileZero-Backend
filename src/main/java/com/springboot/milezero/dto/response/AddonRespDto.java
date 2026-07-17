package com.springboot.milezero.dto.response;

import java.math.BigDecimal;

public record AddonRespDto(
        String name,
        BigDecimal pricePerDay
) {
}
