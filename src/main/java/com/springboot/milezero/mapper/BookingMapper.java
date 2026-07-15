package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.request.BookingReqDto;
import com.springboot.milezero.enums.BookingStatus;
import com.springboot.milezero.model.Booking;

public class BookingMapper {

    private BookingMapper() {
        /* This utility class should not be instantiated */
    }

    public static Booking convertDtoToEntity(BookingReqDto dto) {
        Booking booking = new Booking();
        booking.setPlannedPickup(dto.plannedPickup());
        booking.setPlannedReturn(dto.plannedReturn());
        booking.setIncludedKm(dto.requestedKm());
        booking.setBookingType(dto.bookingType());
        booking.setBookingStatus(BookingStatus.BOOKED);
        return booking;
    }
}
