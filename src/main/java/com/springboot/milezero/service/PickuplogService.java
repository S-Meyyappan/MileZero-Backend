package com.springboot.milezero.service;

import com.springboot.milezero.dto.response.PickupSummaryDto;
import com.springboot.milezero.dto.response.PickuplogRespDto;
import com.springboot.milezero.enums.*;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.mapper.PickupImageMapper;
import com.springboot.milezero.mapper.PickuplogMapper;
import com.springboot.milezero.mapper.PickuplogSummaryMapper;
import com.springboot.milezero.model.*;
import com.springboot.milezero.repository.*;
import com.springboot.milezero.utility.BookingPageableUtil;
import com.springboot.milezero.utility.PickuplogFilterSpecification;
import com.springboot.milezero.utility.UploadUtility;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public PickuplogRespDto add(String email, long odometer, FuelLevel fuelLevel, String remarks, long bookingId, List<MultipartFile> imageFiles) {
        // Step 1. Validate id (vehicle, booking, employee) if exist
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new ResourceNotFoundException("Booking Id Invalid"));
        Employee employee = employeeRepository.fetchByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Employee Invalid"));
        Vehicle vehicle = vehicleRepository.findById(booking.getVehicle().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Vehicle Invalid"));

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


    public Page<PickupSummaryDto> getAllByEmployee(String email, String search, BookingType bookingType, BookingStatus bookingStatus, String category, Instant startDate, Instant endDate, int page, int size, FilterSort filterSort) {
        Employee employee = employeeRepository.fetchByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        Pageable pageable = BookingPageableUtil.getPageable(page, size, filterSort);

        // Pass employee branchId
        long branchId = employee.getBranch().getId();

        Specification<Pickuplog> spec = PickuplogFilterSpecification.getFilterSpecification(branchId, search, bookingType, bookingStatus, category, startDate, endDate);

        // Fetch paginated and filtered data
        Page<Pickuplog> pickuplogs = pickuplogRepository.findAll(spec, pageable);

        List<Long> pickupIds = pickuplogs.getContent().stream()
                                .map(Pickuplog::getId)
                                .toList();

        List<PickupImage> pickupImageList = pickupImageRepository.findByPickuplogIn(pickupIds);

        Map<Long, List<String>> pickupImageMap = pickupImageList.stream()
                .collect(Collectors.groupingBy(
                        pickupImage -> pickupImage.getPickuplog().getId(),
                        Collectors.mapping(PickupImage::getUrl, Collectors.toList())
                ));

        return pickuplogs
                .map(pickuplog ->
                        PickuplogSummaryMapper.convertToEntity(
                            pickuplog,
                            pickuplog.getBooking(),
                            pickupImageMap.get(pickuplog.getId())
                ));
    }
}
