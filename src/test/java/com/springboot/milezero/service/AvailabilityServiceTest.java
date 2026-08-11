package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.BranchAvailabliltyReqDto;
import com.springboot.milezero.dto.request.GlobalAvailabilityReqDto;
import com.springboot.milezero.repository.BookingRepository;
import com.springboot.milezero.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

@ExtendWith(MockitoExtension.class)
public class AvailabilityServiceTest {

    @InjectMocks
    private AvailabilityService availabilityService;

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private VehicleRepository vehicleRepository;

    private Instant pickupInstant;
    private Instant returnInstant;

    private GlobalAvailabilityReqDto globalAvailabilityReqDto;
    private BranchAvailabliltyReqDto branchAvailabliltyReqDto;

    @BeforeEach
    public void init() {
        pickupInstant = Instant.parse("2026-08-10T10:00:00Z");
        returnInstant = Instant.parse("2026-08-10T15:00:00Z");
        globalAvailabilityReqDto = new GlobalAvailabilityReqDto(pickupInstant, returnInstant);
        branchAvailabliltyReqDto = new BranchAvailabliltyReqDto(pickupInstant, returnInstant, 1L);
    }

    @Test
    @DisplayName("Get Availability Test")
    void shouldGetAvailability() {

    }

}
