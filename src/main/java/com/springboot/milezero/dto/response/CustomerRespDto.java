package com.springboot.milezero.dto.response;

import java.time.LocalDate;

public record CustomerRespDto(
        String name,
        String licenseNo,
        LocalDate licenseExpiryDate,
        String phone,
        String insurancePolicyNo
) {
}
