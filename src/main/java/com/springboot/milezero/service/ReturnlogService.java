package com.springboot.milezero.service;

import com.springboot.milezero.dto.response.BookingRespDto;
import com.springboot.milezero.dto.response.ReturnlogRespDto;
import com.springboot.milezero.enums.BookingStatus;
import com.springboot.milezero.enums.CurrentStatus;
import com.springboot.milezero.enums.FuelLevel;
import com.springboot.milezero.exception.InvalidReqException;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.mapper.ReturnImageMapper;
import com.springboot.milezero.mapper.ReturnlogMapper;
import com.springboot.milezero.model.*;
import com.springboot.milezero.repository.*;
import com.springboot.milezero.utility.ReturnPricingUtil;
import com.springboot.milezero.utility.UploadUtility;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReturnlogService {

    private final ReturnlogRepository returnlogRepository;
    private final BookingRepository bookingRepository;
    private final EmployeeRepository employeeRepository;
    private final ReturnPricingUtil returnPricingUtil;
    private final VehicleRepository vehicleRepository;
    private final ReturnImageRepository returnImageRepository;
    private final BookingService bookingService;

    private static final Logger log = LoggerFactory.getLogger(ReturnlogService.class);
    private static final String UPLOAD_DIR = "D:/MileZero/MileZero-Frontend/public/return";


    @Transactional
    public ReturnlogRespDto add(String email, long odometer, FuelLevel fuelLevel, String remarks, long bookingId, List<MultipartFile> imageFiles) {
        // Validate if they exist
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking Invalid"));
        Employee employee = employeeRepository.fetchByEmail(email).
                orElseThrow(() -> new ResourceNotFoundException("Employee Invalid"));

        // Validate if the booking is active
        if (booking.getBookingStatus() != BookingStatus.ACTIVE) {
            throw new InvalidReqException("Booking is not Active. Cannot record return");
        }

        // Get actual return time
        Instant actualReturn = Instant.now();

        // Calc extra charges
        BigDecimal extraCharges = returnPricingUtil.calculateExtraCharges(odometer, actualReturn, booking);
        log.info("Extra Charges: {}", extraCharges);
        BigDecimal finalPrice = extraCharges.add(booking.getEstimatedCost());
        log.info("Final Price: {}", finalPrice);

        // Get Vehicle from booking
        Vehicle vehicle = booking.getVehicle();
        vehicle.setCurrentStatus(CurrentStatus.AVAILABLE);
        vehicle.setCurrentOdometer(odometer);

        // Update finalcost and status in booking
        booking.setFinalCost(finalPrice);
        booking.setBookingStatus(BookingStatus.RETURNED);

        // Get Returnlog from mapper
        Returnlog returnlog = ReturnlogMapper.createEntity(odometer, actualReturn, fuelLevel, remarks, booking, employee, extraCharges);

        // Save Vehicle, Booking and Returnlog
        vehicleRepository.save(vehicle);
        bookingRepository.save(booking);

        if (!imageFiles.isEmpty()) {
            // 5.1 Validate files
            UploadUtility.validateMultipleFiles(imageFiles);

            // 5.2 Save all images and collect file names
            List<String> fileNames = imageFiles.stream()
                    .map(f -> UploadUtility.saveImage(f, UPLOAD_DIR))
                    .toList();

            // 5.3 PickupImage entities creation
            List<ReturnImage> returnImages = fileNames.stream()
                    .map(fileName -> ReturnImageMapper.createEntity(fileName, booking, returnlog))
                    .toList();

            // 5.4 Save all pickup images
            returnImageRepository.saveAll(returnImages);
        }

        Returnlog finalReturnlog = returnlogRepository.save(returnlog);

        // Get Booking from returnlog
        BookingRespDto finalBooking = bookingService.get(returnlog.getBooking().getId());

        return ReturnlogMapper.convertEntityToDto(finalReturnlog, finalBooking);
    }

//    public BigDecimal calc(ReturnlogReqDto returnlogReqDto){
//        // Validate if they exist
//        Booking booking = bookingRepository.findById(returnlogReqDto.bookingId())
//                .orElseThrow(() -> new ResourceNotFoundException("Booking Id Invalid"));
//
//        // Calc extra charges
//        return returnPricingUtil.calculateExtraCharges(returnlogReqDto, booking);
//    }

//    public ReturnlogRespDto getByBookingId(long id) {
//        Returnlog returnlog = returnlogRepository.findByBookingId(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Booking Id Invalid"));
//
//        return ReturnlogMapper.convertEntityToDto(returnlog);
//    }
}
