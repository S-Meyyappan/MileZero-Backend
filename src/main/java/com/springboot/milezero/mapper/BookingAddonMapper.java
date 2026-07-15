package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.request.BookingAddonDto;
import com.springboot.milezero.dto.util.AddonDtoUtil;
import com.springboot.milezero.model.Booking;
import com.springboot.milezero.model.BookingAddon;

public class BookingAddonMapper {

    private BookingAddonMapper() {
        /* This utility class should not be instantiated */
    }

    public static BookingAddon convertDtoToEntity(BookingAddonDto dto){
        BookingAddon bookingAddon = new BookingAddon();
        bookingAddon.setQuantity(dto.quantity());
        return bookingAddon;
    }

    public static BookingAddon convertDtoToEntity(AddonDtoUtil dto, Booking booking){
        BookingAddon bookingAddon = new BookingAddon();
        bookingAddon.setAddon(dto.addon());
        bookingAddon.setQuantity(dto.quantity());
        bookingAddon.setBooking(booking);
        return bookingAddon;
    }
}
