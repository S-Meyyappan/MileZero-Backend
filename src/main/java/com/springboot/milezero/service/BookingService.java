package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.BookingAddonDto;
import com.springboot.milezero.dto.request.BookingReqDto;
import com.springboot.milezero.dto.response.BookingQuoteRespDto;
import com.springboot.milezero.dto.util.AddonDtoUtil;
import com.springboot.milezero.enums.BookingType;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.mapper.BookingAddonMapper;
import com.springboot.milezero.mapper.BookingMapper;
import com.springboot.milezero.model.*;
import com.springboot.milezero.repository.*;
import com.springboot.milezero.utility.BookingPricingUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingAddonRepository bookingAddonRepository;
    private final BranchRepository branchRepository;
    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;
    private final AddonRepository addonRepository;

    private final BookingPricingUtil bookingPricingUtil;

    @Transactional
    public void add(@Valid BookingReqDto bookingReqDto) {
        // Validate all ids(pickup branch, return branch, customer, category) present in dto
        Branch pickupBranch = branchRepository.findById(bookingReqDto.pickupBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Pickup branch not found"));
        Branch returnBranch = branchRepository.findById(bookingReqDto.returnBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Return branch not found"));
        Customer customer = customerRepository.findById(bookingReqDto.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        Category category = categoryRepository.findById(bookingReqDto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

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

        // Attach customer, category, pickup branch, return branch to booking
        booking.setCustomer(customer);
        booking.setCategory(category);
        booking.setPickupBranch(pickupBranch);
        booking.setReturnBranch(returnBranch);
        booking.setBookedRate(
                bookingReqDto.bookingType().equals(BookingType.HOURLY)
                        ? category.getBasePricePerHour()
                        : category.getBasePricePerDay()
        );
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
    }

    public BookingQuoteRespDto getQuote(@Valid BookingReqDto bookingReqDto) {
        // Validate Category
        Category category = categoryRepository.findById(bookingReqDto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        //Validate addon ids
        List<BookingAddonDto> list = bookingReqDto.bookingAddons();

        list.forEach(bookingAddonDto ->
                        addonRepository.findById(bookingAddonDto.addonId())
                        .orElseThrow(() -> new ResourceNotFoundException("Addon not found for id : "+bookingAddonDto.addonId())));

        // Calculate duration between both days with milliseconds difference
        Duration duration = Duration.between(bookingReqDto.plannedPickup(), bookingReqDto.plannedReturn());

        // Calculate totalKm
        int totalKm = bookingReqDto.requestedKm()+category.getIncludedKmPerDay();

        // Calculate base price
        BigDecimal basePrice = bookingReqDto.bookingType().equals(BookingType.HOURLY)
                ? bookingPricingUtil.calculateBasePriceForHourly(category, totalKm, duration)
                : bookingPricingUtil.calculateBasePriceForDaily(category, totalKm, duration);

        // Calculate addon price
        BigDecimal addonPrice = bookingPricingUtil.calculateAddonPrice(list, duration.toDays());

        // Calculate estimated price
        BigDecimal estimatedPrice = basePrice.add(addonPrice);

        return new BookingQuoteRespDto(
                basePrice,
                addonPrice,
                estimatedPrice,
                totalKm
        );
    }
}
