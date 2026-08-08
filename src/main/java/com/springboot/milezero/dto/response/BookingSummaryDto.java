package com.springboot.milezero.dto.response;

public record BookingSummaryDto(
        BookingRespDto booking,
        PickuplogRespDto pickuplog,
        ReturnlogRespDto returnlog
) {
}
