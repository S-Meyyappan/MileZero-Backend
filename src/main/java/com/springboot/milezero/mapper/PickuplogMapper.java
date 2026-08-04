package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.request.PickuplogReqDto;
import com.springboot.milezero.dto.response.PickuplogRespDto;
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

    public static PickuplogRespDto convertEntityToDto(Pickuplog pickuplog) {
        return new PickuplogRespDto(
                pickuplog.getId(),
                pickuplog.getActualPickup(),
                pickuplog.getOdometer(),
                pickuplog.getFuelLevel(),
                pickuplog.getRemarks(),
                pickuplog.getEmployee().getUser().getName()
           );
    }
}