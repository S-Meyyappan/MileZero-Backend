package com.springboot.milezero.dto.request;

import com.springboot.milezero.enums.DriveType;
import com.springboot.milezero.enums.FuelType;
import com.springboot.milezero.enums.Transmission;

import java.time.LocalDate;
import java.util.List;

public record VehicleReqDto(
        String registrationNo,
        String chassisNo,
        String manufacturer,
        String model,
        int manufacturingYear,
        FuelType fuelType,
        Transmission transmission,
        DriveType driveType,
        long currentOdometer,
        LocalDate purchaseDate,
        Integer seatCount,
        Integer luggageCapacity,
        Boolean withAc,
        Long categoryId,
        Long branchId,
        List<Long> featureIds
) {
}
