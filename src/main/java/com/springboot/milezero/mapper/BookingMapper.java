package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.request.BookingReqDto;
import com.springboot.milezero.model.Booking;

public class BookingMapper {

    private BookingMapper() {
        /* This utility class should not be instantiated */
    }

    public static Booking convertDtoToEntity(BookingReqDto dto) {
        Booking booking = new Booking();
        booking.setPlannedPickup(dto.plannedPickup());
        booking.setPlannedReturn(dto.plannedReturn());
        booking.setIncludedKm(dto.includedKm());
        booking.setBookedRate(dto.bookedRate());
        booking.setEstimatedCost(dto.estimatedCost());
        booking.setFinalCost(dto.finalCost());
        booking.setBookingStatus(dto.bookingStatus());
        return booking;
    }
}
