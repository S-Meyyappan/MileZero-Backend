package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.request.PickuplogReqDto;
import com.springboot.milezero.model.Pickuplog;

public class PickuplogMapper {

    public static Pickuplog convertDtoToEntity(PickuplogReqDto dto) {
        Pickuplog pickuplog = new Pickuplog();
        pickuplog.setActualPickup(dto.actualPickup());
        pickuplog.setOdometer(dto.odometer());
        pickuplog.setFuelLevel(dto.fuelLevel());
        pickuplog.setRemarks(dto.remarks());
        return pickuplog;
    }
}
