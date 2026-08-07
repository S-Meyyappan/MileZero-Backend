package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.response.BookingRespDto;
import com.springboot.milezero.dto.response.ReturnlogRespDto;
import com.springboot.milezero.enums.FuelLevel;
import com.springboot.milezero.model.Booking;
import com.springboot.milezero.model.Employee;
import com.springboot.milezero.model.Returnlog;

import java.math.BigDecimal;
import java.time.Instant;

public class ReturnlogMapper {

    public static Returnlog createEntity(Long odometer, Instant actualReturn, FuelLevel fuelLevel, String remarks, Booking booking, Employee employee, BigDecimal finalPrice){
        Returnlog returnlog = new Returnlog();
        returnlog.setActualReturn(actualReturn);
        returnlog.setOdometer(odometer);
        returnlog.setFuelLevel(fuelLevel);
        returnlog.setRemarks(remarks);
        // Attach booking, employee, finalPrice to returnlog
        returnlog.setBooking(booking);
        returnlog.setExtraCharges(finalPrice);
        returnlog.setEmployee(employee);
        return returnlog;
    }

    public static ReturnlogRespDto convertEntityToDto(Returnlog returnlog, BookingRespDto bookingRespDto){
        return new ReturnlogRespDto(
                returnlog.getId(),
                returnlog.getActualReturn(),
                returnlog.getOdometer(),
                returnlog.getFuelLevel(),
                returnlog.getExtraCharges(),
                returnlog.getRemarks(),
                bookingRespDto,
                returnlog.getEmployee().getUser().getName()
        );
    }
}
