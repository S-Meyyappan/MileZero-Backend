package com.springboot.milezero.service;

import com.springboot.milezero.dto.response.PickuplogRespDto;
import com.springboot.milezero.enums.BookingStatus;
import com.springboot.milezero.enums.CurrentStatus;
import com.springboot.milezero.enums.FuelLevel;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.mapper.PickupImageMapper;
import com.springboot.milezero.mapper.PickuplogMapper;
import com.springboot.milezero.model.*;
import com.springboot.milezero.repository.*;
import com.springboot.milezero.utility.UploadUtility;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PickuplogService {

    private static final Logger log = LoggerFactory.getLogger(PickuplogService.class);
    private final PickuplogRepository pickuplogRepository;
    private final BookingRepository bookingRepository;
    private final EmployeeRepository employeeRepository;
    private final VehicleRepository vehicleRepository;
    private final PickupImageRepository pickupImageRepository;

    private static final String UPLOAD_DIR = "D:/MileZero/MileZero-Frontend/public/pickup";

    @Transactional
    public PickuplogRespDto add(String email, long odometer, FuelLevel fuelLevel, String remarks, long vehicleId, long bookingId, List<MultipartFile> imageFiles) {
        // Step 1. Validate id (vehicle, booking, employee) if exist
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new ResourceNotFoundException("Booking Id Invalid"));
        Employee employee = employeeRepository.fetchByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Employee Invalid"));
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new ResourceNotFoundException("Vehicle Id Invalid"));

        // Step 2. Update the vehicle being allocated and status into booking
        booking.setVehicle(vehicle);
        booking.setBookingStatus(BookingStatus.ACTIVE);
        bookingRepository.save(booking);

        // Step 3. Update the vehicle status
        vehicle.setCurrentStatus(CurrentStatus.RENTED);
        vehicleRepository.save(vehicle);

        // Step 4. Get Pickuplog from dto using mapper
        Pickuplog pickuplog = PickuplogMapper.createEntity(odometer, fuelLevel, remarks, booking, employee);

        // 5. Image Upload
        log.info("Files {}", imageFiles);
        log.info("Files size {}", imageFiles.size());
        log.info("Files empty {}", imageFiles.isEmpty());

        if (!imageFiles.isEmpty()) {
            // 5.1 Validate files
            UploadUtility.validateMultipleFiles(imageFiles);

            // 5.2 Save all images and collect file names
            List<String> fileNames = imageFiles.stream()
                    .map(f -> UploadUtility.saveImage(f, UPLOAD_DIR))
                    .toList();

            // 5.3 PickupImage entities creation
            List<PickupImage> pickupImages = fileNames.stream()
                    .map(fileName -> PickupImageMapper.createEntity(fileName, booking, pickuplog))
                    .toList();

            // 5.4 Save all pickup images
            pickupImageRepository.saveAll(pickupImages);
        }

        // Step 6. Save the pickuplog
        Pickuplog finalPickuplog = pickuplogRepository.save(pickuplog);

        return PickuplogMapper.convertEntityToDto(finalPickuplog);
    }

    public PickuplogRespDto getByBookingId(long id) {
        Pickuplog pickuplog = pickuplogRepository.findByBookingId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking Id Invalid"));

        return PickuplogMapper.convertEntityToDto(pickuplog);
    }
}
