package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.request.VehicleReqDto;
import com.springboot.milezero.dto.response.VehicleDto;
import com.springboot.milezero.dto.response.VehicleRespDto;
import com.springboot.milezero.model.Feature;
import com.springboot.milezero.model.Vehicle;

import java.util.List;

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
        vehicle.setSeatCount(dto.seatCount());
        vehicle.setLuggageCapacity(dto.luggageCapacity());
        return vehicle;
    }

    public static VehicleRespDto convertEntityToDto(Vehicle vehicle, List<Feature> featuresList) {
        return  new VehicleRespDto(
                vehicle.getId(),
                vehicle.getManufacturer(),
                vehicle.getModel(),
                vehicle.getManufacturingYear(),
                vehicle.getFuelType(),
                vehicle.getTransmission(),
                vehicle.getDriveType(),
                vehicle.getWithAc(),
                vehicle.getSeatCount(),
                vehicle.getLuggageCapacity(),
                vehicle.getCurrentOdometer(),
                CategoryMapper.convertEntityToDto(vehicle.getCategory()),
                featuresList
        );
    }

    public static VehicleDto convertEntityToDto(Vehicle vehicle) {
        return  new VehicleDto(
                vehicle.getId(),
                vehicle.getManufacturer(),
                vehicle.getModel(),
                vehicle.getManufacturingYear(),
                vehicle.getFuelType(),
                vehicle.getTransmission(),
                vehicle.getDriveType(),
                vehicle.getWithAc(),
                vehicle.getSeatCount(),
                vehicle.getLuggageCapacity(),
                CategoryMapper.convertEntityToDto(vehicle.getCategory())
        );
    }
}
