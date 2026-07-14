package com.springboot.milezero.mapper.request;

import com.springboot.milezero.dto.request.BookingAddonDto;
import com.springboot.milezero.model.BookingAddon;

public class BookingAddonReqMapper {

    public static BookingAddon convertDtoToEntity(BookingAddonDto dto){
        BookingAddon bookingAddon = new BookingAddon();
        bookingAddon.setQuantity(dto.quantity());
        return bookingAddon;
    }
}
