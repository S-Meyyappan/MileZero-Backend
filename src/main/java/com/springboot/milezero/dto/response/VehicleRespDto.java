package com.springboot.milezero.dto.response;

import com.springboot.milezero.enums.DriveType;
import com.springboot.milezero.enums.FuelType;
import com.springboot.milezero.enums.Transmission;

public record VehicleRespDto(
        String manufacturer,
        String model,
        int manufacturingYear,
        FuelType fuelType,
        Transmission transmission,
        DriveType driveType,
        Boolean withAc,
        Integer seatCount,
        Integer luggageCapacity
) {
}
