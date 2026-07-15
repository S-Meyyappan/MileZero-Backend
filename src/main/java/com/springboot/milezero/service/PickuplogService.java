package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.PickuplogReqDto;
import com.springboot.milezero.enums.BookingStatus;
import com.springboot.milezero.enums.CurrentStatus;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.mapper.PickuplogMapper;
import com.springboot.milezero.model.Booking;
import com.springboot.milezero.model.Employee;
import com.springboot.milezero.model.Pickuplog;
import com.springboot.milezero.model.Vehicle;
import com.springboot.milezero.repository.BookingRepository;
import com.springboot.milezero.repository.EmployeeRepository;
import com.springboot.milezero.repository.PickuplogRepository;
import com.springboot.milezero.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PickuplogService {

    private final PickuplogRepository pickuplogRepository;
    private final BookingRepository bookingRepository;
    private final EmployeeRepository employeeRepository;
    private final VehicleRepository vehicleRepository;

    @Transactional
    public void add(@Valid PickuplogReqDto pickuplogReqDto) {
        // Step 1. Validate id (vehicle, booking, employee) if exist
        Booking booking = bookingRepository.findById(pickuplogReqDto.bookingId())
            .orElseThrow(() -> new ResourceNotFoundException("Booking Id Invalid"));
        Employee employee = employeeRepository.findById(pickuplogReqDto.employeeId())
            .orElseThrow(() -> new ResourceNotFoundException("Employee Id Invalid"));
        Vehicle vehicle = vehicleRepository.findById(pickuplogReqDto.vehicleId())
            .orElseThrow(() -> new ResourceNotFoundException("Vehicle Id Invalid"));

        // Step 2. Update the vehicle being allocated and status into booking
        booking.setVehicle(vehicle);
        booking.setBookingStatus(BookingStatus.ACTIVE);
        bookingRepository.save(booking);

        // Step 3. Update the vehicle status
        vehicle.setCurrentStatus(CurrentStatus.RENTED);
        vehicleRepository.save(vehicle);

        // Step 4. Get Pickuplog from dto using mapper
        Pickuplog pickuplog = PickuplogMapper.convertDtoToEntity(pickuplogReqDto);
        pickuplog.setBooking(booking);
        pickuplog.setEmployee(employee);

        // Step 5. Save the pickuplog
        pickuplogRepository.save(pickuplog);
    }
}
