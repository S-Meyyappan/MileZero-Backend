package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.BookingAddonDto;
import com.springboot.milezero.dto.request.BookingReqDto;
import com.springboot.milezero.dto.response.BookingQuoteRespDto;
import com.springboot.milezero.dto.response.BookingRespDto;
import com.springboot.milezero.dto.util.AddonDtoUtil;
import com.springboot.milezero.enums.BookingStatus;
import com.springboot.milezero.enums.BookingType;
import com.springboot.milezero.enums.FilterSort;
import com.springboot.milezero.exception.InvalidReqException;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.mapper.BookingAddonMapper;
import com.springboot.milezero.mapper.BookingMapper;
import com.springboot.milezero.model.*;
import com.springboot.milezero.repository.*;
import com.springboot.milezero.utility.BookingFilterSpecification;
import com.springboot.milezero.utility.BookingPageableUtil;
import com.springboot.milezero.utility.BookingPricingUtil;
import com.springboot.milezero.utility.DurationCalcUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);
    private final BookingRepository bookingRepository;
    private final BookingAddonRepository bookingAddonRepository;
    private final BranchRepository branchRepository;
    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;
    private final AddonRepository addonRepository;
    private final VehicleRepository vehicleRepository;
    private final EmployeeRepository employeeRepository;

    private final BookingPricingUtil bookingPricingUtil;

    @Transactional
    public BookingRespDto add(@Valid BookingReqDto bookingReqDto) {
        // Validate all ids(pickup branch, return branch, customer, category) present in dto
        Branch pickupBranch = branchRepository.findById(bookingReqDto.pickupBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Pickup branch not found"));
        Branch returnBranch = branchRepository.findById(bookingReqDto.returnBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Return branch not found"));
        Customer customer = customerRepository.findById(bookingReqDto.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        Vehicle vehicle = vehicleRepository.findById(bookingReqDto.vehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
        Category category = vehicle.getCategory();

        //Validate addon ids
        List<BookingAddonDto> list = bookingReqDto.bookingAddons();

        List<AddonDtoUtil> addonDtoUtils = list.stream()
                .map(bookingAddonDto->{
                    Addon addon = addonRepository.findById(bookingAddonDto.addonId())
                            .orElseThrow(() -> new ResourceNotFoundException("Addon not found for id : "+bookingAddonDto.addonId()));
                    return new AddonDtoUtil(
                            addon,
                            bookingAddonDto.quantity());
                })
                .toList();

        // Get booking from dto using mapper
        Booking booking = BookingMapper.convertDtoToEntity(bookingReqDto);

        // Get booking quote
        BookingQuoteRespDto quote = getQuote(bookingReqDto);

        // Attach customer, category, pickup branch, return branch, vehicle to booking
        booking.setVehicle(vehicle);
        booking.setCustomer(customer);
        booking.setCategory(category);
        booking.setPickupBranch(pickupBranch);
        booking.setReturnBranch(returnBranch);
        booking.setBookedRate(
                bookingReqDto.bookingType().equals(BookingType.HOURLY)
                        ? category.getBasePricePerHour()
                        : category.getBasePricePerDay()
        );
        booking.setDuration(quote.duration());
        booking.setEstimatedCost(quote.totalEstimateCost());
        booking.setIncludedKm(quote.totalIncludedKm());

        // Add booking to db
        final Booking savedBooking = bookingRepository.save(booking);

        // Map addonDtoUtils to bookingAddons and add Booking to each
        List<BookingAddon> bookingAddons = addonDtoUtils.stream()
                .map(addonDto -> BookingAddonMapper.convertDtoToEntity(addonDto, savedBooking))
                .toList();

        // Batch save all bookingAddons to db
        bookingAddonRepository.saveAll(bookingAddons);

        List<BookingAddon> bookingAddonList = bookingAddonRepository.findAllByBooking(savedBooking.getId());

        return BookingMapper.convertEntityToDto(savedBooking, bookingAddonList);
    }

    public BookingQuoteRespDto getQuote(@Valid BookingReqDto bookingReqDto) {
        // Validate Category
        Vehicle vehicle = vehicleRepository.findById(bookingReqDto.vehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
        Category category = vehicle.getCategory();

        //Validate addon ids
        List<BookingAddonDto> list = bookingReqDto.bookingAddons();

        list.forEach(bookingAddonDto ->
                        addonRepository.findById(bookingAddonDto.addonId())
                        .orElseThrow(() -> new ResourceNotFoundException("Addon not found for id : "+bookingAddonDto.addonId())));

        // Calculate duration between both days as days and hours
        Duration duration = Duration.between(bookingReqDto.plannedPickup(), bookingReqDto.plannedReturn());
        BigDecimal days = DurationCalcUtil.getDurationOfDays(duration);
        BigDecimal hours = DurationCalcUtil.getDurationOfHours(duration);

        // Calculate totalKm
        int requestedKm = bookingReqDto.requestedKm();
        int totalKm = requestedKm + category.getIncludedKmPerDay();

        // Calculate base price
        BigDecimal basePrice = bookingReqDto.bookingType().equals(BookingType.HOURLY)
                ? bookingPricingUtil.calculateBasePriceForHourly(category, requestedKm, hours)
                : bookingPricingUtil.calculateBasePriceForDaily(category, requestedKm, days);

        // Calculate addon price
        BigDecimal addonPrice = bookingPricingUtil.calculateAddonPrice(list, days);

        // Calculate estimated price
        BigDecimal estimatedPrice = basePrice.add(addonPrice);

        return new BookingQuoteRespDto(
                basePrice.setScale(2, RoundingMode.HALF_UP),
                addonPrice.setScale(2, RoundingMode.HALF_UP),
                estimatedPrice.setScale(2, RoundingMode.HALF_UP),
                totalKm,
                bookingReqDto.bookingType().equals(BookingType.HOURLY) ? hours : days
        );
    }

    public BookingRespDto get(long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking Id Invalid"));

        List<BookingAddon> bookingAddonList = bookingAddonRepository.findAllByBooking(booking.getId());

        return BookingMapper.convertEntityToDto(booking, bookingAddonList);
    }

    public List<BookingRespDto> getByBranch(long id) {
        List<Booking> list = bookingRepository.findByBranchId(id);

        //Get All Booking Ids
        List<Long> bookingIds = list.stream().map(Booking::getId).toList();

        //Get All Booking Addons
        List<BookingAddon> bookingAddonList = bookingAddonRepository.findAllByBookingIdIn(bookingIds);

        //Map BookingId to BookingAddons
        Map<Long, List<BookingAddon>> bookingAddonMap = bookingAddonList.stream()
                .collect(
                        Collectors.groupingBy(b -> b.getBooking().getId())
                );

        return list
                .stream()
                .map(b -> BookingMapper.
                        convertEntityToDto(
                                b,
                                bookingAddonMap.getOrDefault(b.getId(), List.of()) // If no booking addons exist of this booking, return empty list
                        ))
                .toList();
    }

    public List<BookingRespDto> getByVehicle(long id) {
        List<Booking> list = bookingRepository.findByVehicleId(id);

        //Get All Booking Ids
        List<Long> bookingIds = list.stream().map(Booking::getId).toList();

        //Get All Booking Addons
        List<BookingAddon> bookingAddonList = bookingAddonRepository.findAllByBookingIdIn(bookingIds);

        //Map BookingId to BookingAddons
        Map<Long, List<BookingAddon>> bookingAddonMap = bookingAddonList.stream()
                .collect(
                        Collectors.groupingBy(b -> b.getBooking().getId())
                );

        return list
                .stream()
                .map(b -> BookingMapper.
                        convertEntityToDto(
                                b,
                                bookingAddonMap.getOrDefault(b.getId(), List.of()) // If no booking addons exist of this booking, return empty list
                        ))
                .toList();
    }

    public Page<BookingRespDto> getByCustomer(String email, String search, BookingType bookingType, BookingStatus bookingStatus, String category, int page, int size, FilterSort filterSort) {

        Pageable pageable = BookingPageableUtil.getPageable(page, size, filterSort);

        Specification<Booking> spec = BookingFilterSpecification.hasCustomerEmail(email)
                                    .and(BookingFilterSpecification.getFilterSpecification(
                                                search,
                                                bookingType,
                                                bookingStatus,
                                                category
                                        ));

        Page<Booking> list = bookingRepository.findAll(spec, pageable);

        //Get All Booking Ids
        List<Long> bookingIds = list.stream().map(Booking::getId).toList();

        //Get All Booking Addons
        List<BookingAddon> bookingAddonList = bookingAddonRepository.findAllByBookingIdIn(bookingIds);

        //Map BookingId to BookingAddons
        Map<Long, List<BookingAddon>> bookingAddonMap = bookingAddonList.stream()
                .collect(
                        Collectors.groupingBy(b -> b.getBooking().getId())
                );

        return list
                .map(b -> BookingMapper.
                        convertEntityToDto(
                                b,
                                bookingAddonMap.getOrDefault(b.getId(), List.of()) // If no booking addons exist of this booking, return empty list
                        ));
    }

    public BookingRespDto postByLoggedInCustomer(String email, BookingReqDto bookingReqDto) {
        // Validate all ids(pickup branch, return branch, customer, category) present in dto
        Branch pickupBranch = branchRepository.findById(bookingReqDto.pickupBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Pickup branch not found"));
        Branch returnBranch = branchRepository.findById(bookingReqDto.returnBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Return branch not found"));
        Customer customer = customerRepository.fetchByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        Vehicle vehicle = vehicleRepository.findById(bookingReqDto.vehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
        Category category = vehicle.getCategory();

        // Validate Whether Vehicle is Available or Not
        List<Booking> overlapBookings = bookingRepository.checkOverlap(vehicle.getId(), bookingReqDto.plannedPickup(), bookingReqDto.plannedReturn());

        if(!overlapBookings.isEmpty()) {
            throw new ResourceNotFoundException("Vehicle is not available for the given dates");
        }
        //Validate addon ids
        List<BookingAddonDto> list = bookingReqDto.bookingAddons();

        List<AddonDtoUtil> addonDtoUtils = list.stream()
                .map(bookingAddonDto->{
                    Addon addon = addonRepository.findById(bookingAddonDto.addonId())
                            .orElseThrow(() -> new ResourceNotFoundException("Addon not found for id : "+bookingAddonDto.addonId()));
                    return new AddonDtoUtil(
                            addon,
                            bookingAddonDto.quantity());
                })
                .toList();

        // Get booking from dto using mapper
        Booking booking = BookingMapper.convertDtoToEntity(bookingReqDto);

        // Get booking quote
        BookingQuoteRespDto quote = getQuote(bookingReqDto);

        // Attach customer, category, pickup branch, return branch, vehicle to booking
        booking.setVehicle(vehicle);
        booking.setCustomer(customer);
        booking.setCategory(category);
        booking.setPickupBranch(pickupBranch);
        booking.setReturnBranch(returnBranch);
        booking.setBookedRate(
                bookingReqDto.bookingType().equals(BookingType.HOURLY)
                        ? category.getBasePricePerHour()
                        : category.getBasePricePerDay()
        );
        booking.setDuration(quote.duration());
        booking.setEstimatedCost(quote.totalEstimateCost());
        booking.setIncludedKm(quote.totalIncludedKm());

        // Add booking to db
        final Booking savedBooking = bookingRepository.save(booking);

        // Map addonDtoUtils to bookingAddons and add Booking to each
        List<BookingAddon> bookingAddons = addonDtoUtils.stream()
                .map(addonDto -> BookingAddonMapper.convertDtoToEntity(addonDto, savedBooking))
                .toList();

        // Batch save all bookingAddons to db
        bookingAddonRepository.saveAll(bookingAddons);

        List<BookingAddon> bookingAddonList = bookingAddonRepository.findAllByBooking(savedBooking.getId());

        return BookingMapper.convertEntityToDto(savedBooking, bookingAddonList);
    }

    public Page<BookingRespDto> getByBranchLoggedInEmp(String email, String search, BookingType bookingType, BookingStatus bookingStatus, String category, int page, int size, FilterSort filterSort) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        Pageable pageable = BookingPageableUtil.getPageable(page, size, filterSort);

        Specification<Booking> spec = BookingFilterSpecification.hasEmployeeBranch(employee.getBranch().getId())
                                        .and(BookingFilterSpecification.getFilterSpecification(
                                                search,
                                                bookingType,
                                                bookingStatus,
                                                category));

        Page<Booking> list = bookingRepository.findAll(spec, pageable);

        //Get All Booking Ids
        List<Long> bookingIds = list.stream().map(Booking::getId).toList();

        //Get All Booking Addons
        List<BookingAddon> bookingAddonList = bookingAddonRepository.findAllByBookingIdIn(bookingIds);

        //Map BookingId to BookingAddons
        Map<Long, List<BookingAddon>> bookingAddonMap = bookingAddonList.stream()
                .collect(
                        Collectors.groupingBy(b -> b.getBooking().getId())
                );

        return list
                .map(b -> BookingMapper.
                        convertEntityToDto(
                                b,
                                bookingAddonMap.getOrDefault(b.getId(), List.of()) // If no booking addons exist of this booking, return empty list
                        ));

    }

    public void cancelBooking(long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        Instant cancelDate = Instant.now();

        Duration duration = Duration.between(cancelDate, booking.getPlannedPickup());

        if(duration.toHours() < 48) {
            throw new InvalidReqException("Cannot cancel your pickup is more or less than a day");
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    public void changeBookingStatus(long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        Instant markDate = Instant.now();

        Duration duration = Duration.between(markDate, booking.getPlannedPickup());

        if(markDate.isBefore(booking.getPlannedPickup())) {
            throw new InvalidReqException("Cannot mark booking as no show before pickup");
        }

        booking.setBookingStatus(BookingStatus.NO_SHOW);

        bookingRepository.save(booking);
    }
}
