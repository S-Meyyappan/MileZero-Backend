package com.springboot.milezero.dto.response;

public record AvailabileVehicleRespDto(
        Long categoryId,
        String categoryName,
        Long totalVehicles,
        Long bookedVehicles,
        Long vehiclesAvailableToBook
) {
}
