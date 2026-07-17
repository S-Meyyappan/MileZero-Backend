package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.ReturnlogReqDto;
import com.springboot.milezero.enums.BookingStatus;
import com.springboot.milezero.enums.CurrentStatus;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.mapper.ReturnlogMapper;
import com.springboot.milezero.model.Booking;
import com.springboot.milezero.model.Employee;
import com.springboot.milezero.model.Returnlog;
import com.springboot.milezero.model.Vehicle;
import com.springboot.milezero.repository.BookingRepository;
import com.springboot.milezero.repository.EmployeeRepository;
import com.springboot.milezero.repository.ReturnlogRepository;
import com.springboot.milezero.repository.VehicleRepository;
import com.springboot.milezero.utility.ReturnPricingUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ReturnlogService {

    private final ReturnlogRepository returnlogRepository;
    private final BookingRepository bookingRepository;
    private final EmployeeRepository employeeRepository;
    private final ReturnPricingUtil returnPricingUtil;
    private final VehicleRepository vehicleRepository;

    @Transactional
    public void add(@Valid ReturnlogReqDto returnlogReqDto) {
        // Validate if they exist
        Booking booking = bookingRepository.findById(returnlogReqDto.bookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking Id Invalid"));
        Employee employee = employeeRepository.findById(returnlogReqDto.employeeId()).
                orElseThrow(() -> new ResourceNotFoundException("Employee Id Invalid"));

        // Calc extra charges
        BigDecimal extraCharges = returnPricingUtil.calculateExtraCharges(returnlogReqDto, booking);
        BigDecimal finalPrice = extraCharges.add(booking.getEstimatedCost());

        // Get Vehicle from booking
        Vehicle vehicle = booking.getVehicle();
        vehicle.setCurrentStatus(CurrentStatus.AVAILABLE);
        vehicle.setCurrentOdometer(returnlogReqDto.odometer());

        // Update finalcost and status in booking
        booking.setFinalCost(finalPrice);
        booking.setBookingStatus(BookingStatus.RETURNED);

        // Get Returnlog from mapper
        Returnlog returnlog = ReturnlogMapper.convertDtoToEntity(returnlogReqDto);

        // Attach booking, employee, finalPrice to returnlog
        returnlog.setBooking(booking);
        returnlog.setExtraCharges(finalPrice);
        returnlog.setEmployee(employee);

        // Save Vehicle, Booking and Returnlog
        vehicleRepository.save(vehicle);
        bookingRepository.save(booking);
        returnlogRepository.save(returnlog);
    }

    public BigDecimal calc(ReturnlogReqDto returnlogReqDto){
        // Validate if they exist
        Booking booking = bookingRepository.findById(returnlogReqDto.bookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking Id Invalid"));

        // Calc extra charges
        return returnPricingUtil.calculateExtraCharges(returnlogReqDto, booking);
    }
}
