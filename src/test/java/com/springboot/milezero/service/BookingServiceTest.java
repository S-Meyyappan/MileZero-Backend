package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.BookingReqDto;
import com.springboot.milezero.enums.BookingStatus;
import com.springboot.milezero.enums.BookingType;
import com.springboot.milezero.exception.InvalidReqException;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.model.Booking;
import com.springboot.milezero.model.Branch;
import com.springboot.milezero.model.Customer;
import com.springboot.milezero.repository.*;
import com.springboot.milezero.utility.BookingPricingUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingAddonRepository bookingAddonRepository;

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private AddonRepository addonRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private PickuplogRepository pickuplogRepository;

    @Mock
    private ReturnlogRepository returnlogRepository;

    @Mock
    private BookingPricingUtil bookingPricingUtil;

    @Spy
    @InjectMocks
    private BookingService bookingService;

    private Instant pickupInstant;
    private Instant returnInstant;

    @BeforeEach
    void init() {
        pickupInstant = Instant.parse("2026-08-20T10:00:00Z");
        returnInstant = Instant.parse("2026-08-20T15:00:00Z");
    }




    @Test
    @DisplayName("Add Booking - Vehicle Not Found")
    void shouldThrowWhenVehicleNotFound() {

        // Given
        BookingReqDto request = new BookingReqDto(
                pickupInstant,
                returnInstant,
                100,
                3L,
                4L,
                1L,
                2L,
                BookingType.HOURLY,
                List.of()
        );

        when(branchRepository.findById(1L)).thenReturn(Optional.of(new Branch()));
        when(branchRepository.findById(2L)).thenReturn(Optional.of(new Branch()));
        when(customerRepository.findById(3L)).thenReturn(Optional.of(new Customer()));
        when(vehicleRepository.findById(4L)).thenReturn(Optional.empty());

        // When & Then
        Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> bookingService.add(request)
        );

        verify(vehicleRepository).findById(4L);

        verify(bookingRepository, never()).save(any(Booking.class));
    }


    @Test
    @DisplayName("Get Booking - Not Found")
    void shouldThrowWhenBookingNotFound() {

        // Given
        when(bookingRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> bookingService.get(999L)
        );

        verify(bookingRepository).findById(999L);
        verify(bookingAddonRepository, never()).findAllByBooking(999L);
    }


    @Test
    @DisplayName("Cancel Booking")
    void shouldCancelBooking() {

        // Given
        Booking booking = new Booking();
        booking.setId(100L);

        // Pickup is safely more than 48 hours away
        booking.setPlannedPickup(Instant.now().plusSeconds(60 * 60 * 72));

        booking.setBookingStatus(BookingStatus.BOOKED);

        when(bookingRepository.findById(100L)).thenReturn(Optional.of(booking));

        // When
        bookingService.cancelBooking(100L);

        // Then
        Assertions.assertEquals(BookingStatus.CANCELLED,booking.getBookingStatus());

        verify(bookingRepository).save(booking);
    }


    @Test
    @DisplayName("Cancel Booking - Too Late")
    void shouldThrowWhenCancellationIsTooLate() {

        // Given
        Booking booking = new Booking();
        booking.setId(100L);

        // Pickup is only 24 hours away
        booking.setPlannedPickup(Instant.now().plusSeconds(60 * 60 * 24));

        booking.setBookingStatus(BookingStatus.BOOKED);

        when(bookingRepository.findById(100L))
                .thenReturn(Optional.of(booking));

        // When & Then
        Assertions.assertThrows(
                InvalidReqException.class,
                () -> bookingService.cancelBooking(100L)
        );

        verify(bookingRepository, never()).save(any(Booking.class));
    }
}
