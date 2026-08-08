package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.response.BookingRespDto;
import com.springboot.milezero.dto.response.BookingSummaryDto;
import com.springboot.milezero.model.Pickuplog;
import com.springboot.milezero.model.Returnlog;

public class BookingSummaryMapper {

    public static BookingSummaryDto convertEntityToDto(BookingRespDto booking, Pickuplog pickuplog, Returnlog returnlog){
        return new BookingSummaryDto(
                booking,
                PickuplogMapper.convertEntityToDto(pickuplog),
                ReturnlogMapper.convertEntityToDto(returnlog)
        );
    }
}
