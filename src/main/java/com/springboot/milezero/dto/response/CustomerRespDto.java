package com.springboot.milezero.dto.response;

import java.time.LocalDate;

public record CustomerRespDto(
        Long id,
        String name,
        String licenseNo,
        LocalDate licenseExpiryDate,
        String phone,
        String insurancePolicyNo,
        String licensePhoto
) {
}
