package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.response.BookingStatsDto;
import com.springboot.milezero.dto.response.CustomerDashboardDto;
import com.springboot.milezero.model.Booking;
import com.springboot.milezero.model.Customer;

import java.util.List;

public class CustomerDashboardMapper {
    private CustomerDashboardMapper() {
        /* This utility class should not be instantiated */
    }


    public static CustomerDashboardDto convertToDto(Customer customer, BookingStatsDto bookingStatsDto, List<Booking> upcomingBookings, List<Booking> recentBookings){
        return new CustomerDashboardDto(
                CustomerMapper.convertEntityToDto(customer),
                bookingStatsDto,
                upcomingBookings.stream().map(BookingDashboardMapper::convertToDto).toList(),
                recentBookings.stream().map(BookingDashboardMapper::convertToDto).toList()
        );
    }
}
