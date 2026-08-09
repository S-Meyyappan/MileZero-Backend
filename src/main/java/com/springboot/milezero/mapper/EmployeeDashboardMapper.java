package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.response.BookingStatsDto;
import com.springboot.milezero.dto.response.EmployeeDashboardDto;
import com.springboot.milezero.model.Booking;
import com.springboot.milezero.model.Employee;

import java.util.List;

public class EmployeeDashboardMapper {

    public static EmployeeDashboardDto convertToEntity(Employee employee, BookingStatsDto bookingStats, List<Booking> upcomingPickups, List<Booking> upcomingReturns, List<Booking> recentBookings){
        return new EmployeeDashboardDto(
                EmployeeMapper.convertEntityToDto(employee),
                bookingStats,
                upcomingPickups.stream().map(BookingDashboardMapper::convertToDto).toList(),
                upcomingReturns.stream().map(BookingDashboardMapper::convertToDto).toList(),
                recentBookings.stream().map(BookingDashboardMapper::convertToDto).toList()
        );
    }
}
