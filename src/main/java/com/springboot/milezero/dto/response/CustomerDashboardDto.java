package com.springboot.milezero.dto.response;

import java.util.List;

public record CustomerDashboardDto(
        CustomerRespDto customer,
        BookingStatsDto bookingStats,
        List<BookingDashboardDto> upcomingBooking,
        List<BookingDashboardDto> recentBookings
) {
}
