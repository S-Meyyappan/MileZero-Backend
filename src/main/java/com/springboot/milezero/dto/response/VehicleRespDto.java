package com.springboot.milezero.dto.response;

import com.springboot.milezero.enums.DriveType;
import com.springboot.milezero.enums.FuelType;
import com.springboot.milezero.enums.Transmission;
import com.springboot.milezero.model.Feature;

import java.util.List;

public record VehicleRespDto(
        Long id,
        String manufacturer,
        String model,
        Integer manufacturingYear,
        FuelType fuelType,
        Transmission transmission,
        DriveType driveType,
        Boolean withAc,
        Integer seatCount,
        Integer luggageCapacity,
        Long currentOdometer,
        CategoryRespDto category,
        List<Feature> features
) {
}
