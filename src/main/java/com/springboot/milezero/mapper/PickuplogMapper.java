package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.response.PickuplogRespDto;
import com.springboot.milezero.enums.FuelLevel;
import com.springboot.milezero.model.Booking;
import com.springboot.milezero.model.Employee;
import com.springboot.milezero.model.Pickuplog;

import java.time.Instant;

public class PickuplogMapper {

    public static Pickuplog createEntity(Long odometer, FuelLevel fuelLevel, String remarks, Booking booking, Employee employee) {
        Pickuplog pickuplog = new Pickuplog();
        pickuplog.setOdometer(odometer);
        pickuplog.setFuelLevel(fuelLevel);
        pickuplog.setRemarks(remarks);
        pickuplog.setActualPickup(Instant.now()); // Current time
        pickuplog.setBooking(booking);
        pickuplog.setEmployee(employee);
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