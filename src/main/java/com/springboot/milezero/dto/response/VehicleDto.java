package com.springboot.milezero.dto.response;

import com.springboot.milezero.enums.DriveType;
import com.springboot.milezero.enums.FuelType;
import com.springboot.milezero.enums.Transmission;

public record VehicleDto(
        Long id,
        String manufacturer,
        String model,
        String registrationNo,
        Long odometer,
        Integer manufacturingYear,
        FuelType fuelType,
        Transmission transmission,
        DriveType driveType,
        Boolean withAc,
        Integer seatCount,
        String image,
        Integer luggageCapacity,
        CategoryRespDto category
) {
}
