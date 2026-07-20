package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.request.ReturnlogReqDto;
import com.springboot.milezero.dto.response.ReturnlogRespDto;
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

    public static ReturnlogRespDto convertEntityToDto(Returnlog returnlog){
        return new ReturnlogRespDto(
                returnlog.getId(),
                returnlog.getActualReturn(),
                returnlog.getOdometer(),
                returnlog.getFuelLevel(),
                returnlog.getExtraCharges(),
                returnlog.getRemarks(),
                returnlog.getEmployee().getName()
        );
    }
}
