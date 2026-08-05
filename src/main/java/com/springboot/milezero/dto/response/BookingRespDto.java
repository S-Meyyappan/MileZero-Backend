package com.springboot.milezero.dto.response;

import com.springboot.milezero.enums.BookingStatus;
import com.springboot.milezero.enums.BookingType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record BookingRespDto(
        Long bookingId,
        BigDecimal bookedRate,
        BookingType bookingType,
        BookingStatus bookingStatus,
        BigDecimal estimatedCost,
        BigDecimal finalCost,
        BigDecimal duration,
        int includedKm,
        Instant plannedPickup,
        Instant plannedReturn,
        CustomerRespDto customer,
        BranchRespDto pickupBranch,
        BranchRespDto returnBranch,
        VehicleDto vehicle,
        List<BookingAddonRespDto> bookingAddons
) {
}
