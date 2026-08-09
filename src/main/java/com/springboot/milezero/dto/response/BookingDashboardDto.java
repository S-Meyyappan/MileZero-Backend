package com.springboot.milezero.dto.response;

import com.springboot.milezero.enums.BookingStatus;
import com.springboot.milezero.enums.BookingType;

import java.math.BigDecimal;
import java.time.Instant;

public record BookingDashboardDto(
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
        VehicleDto vehicle
) {
}
