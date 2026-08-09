package com.springboot.milezero.dto.response;

import java.util.List;

public record EmployeeDashboardDto(
        EmployeeRespDto employee,
        BookingStatsDto bookingStats,
        List<BookingDashboardDto> upcomingPickups,
        List<BookingDashboardDto> upcomingReturns,
        List<BookingDashboardDto> recentBookings
) {
}
