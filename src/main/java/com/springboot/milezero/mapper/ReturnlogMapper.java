package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.request.ReturnlogReqDto;
import com.springboot.milezero.model.Returnlog;

public class ReturnlogMapper {

    public static Returnlog convertDtoToEntity(ReturnlogReqDto dto){
        Returnlog returnlog = new Returnlog();
        returnlog.setActualReturn(dto.actualReturn());
        returnlog.setOdometer(dto.odometer());
        returnlog.setFuelLevel(dto.fuelLevel());
        returnlog.setRemarks(dto.remarks());
        return returnlog;
    }
}
