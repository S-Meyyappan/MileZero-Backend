package com.springboot.milezero.dto.response;

public record BookingStatsDto(
        long total,
        long booked,
        long active,
        long completed,
        long cancelled,
        long returned,
        long missed
) { }
