package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.request.VehicleReqDto;
import com.springboot.milezero.model.Vehicle;

public class VehicleMapper {

    private VehicleMapper() {
        /* This utility class should not be instantiated */
    }

    public static Vehicle convertDtoToEntity(VehicleReqDto dto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNo(dto.registrationNo());
        vehicle.setChassisNo(dto.chassisNo());
        vehicle.setManufacturer(dto.manufacturer());
        vehicle.setModel(dto.model());
        vehicle.setManufacturingYear(dto.manufacturingYear());
        vehicle.setFuelType(dto.fuelType());
        vehicle.setTransmission(dto.transmission());
        vehicle.setDriveType(dto.driveType());
        vehicle.setCurrentOdometer(dto.currentOdometer());
        vehicle.setPurchaseDate(dto.purchaseDate());
        vehicle.setWithAc(dto.withAc());
        return vehicle;
    }
}
