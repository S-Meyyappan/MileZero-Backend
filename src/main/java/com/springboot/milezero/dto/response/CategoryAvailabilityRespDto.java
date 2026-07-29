package com.springboot.milezero.dto.response;

public record CategoryAvailabilityRespDto(
        Long categoryId,
        String categoryName,
        Long totalVehicles,
        Long bookedVehicles,
        Long vehiclesAvailableToBook
) {
}
