package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.request.BookingAddonDto;
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
}
