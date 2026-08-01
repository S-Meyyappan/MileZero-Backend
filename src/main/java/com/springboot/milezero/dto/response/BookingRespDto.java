package com.springboot.milezero.dto.response;

import com.springboot.milezero.enums.BookingStatus;
import com.springboot.milezero.enums.BookingType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record BookingRespDto(
        long bookingId,
        BigDecimal bookedRate,
        BookingType bookingType,
        BookingStatus bookingStatus,
        BigDecimal estimatedCost,
        BigDecimal finalCost,
        int includedKm,
        LocalDateTime plannedPickup,
        LocalDateTime plannedReturn,
        CategoryRespDto category,
        CustomerRespDto customer,
        VehicleDto vehicle,
        BranchRespDto pickupBranch,
        BranchRespDto returnBranch,
        List<BookingAddonRespDto> bookingAddons
) {
}
