package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.BookingAddonDto;
import com.springboot.milezero.dto.request.BookingReqDto;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.mapper.request.BookingAddonReqMapper;
import com.springboot.milezero.mapper.request.BookingReqMapper;
import com.springboot.milezero.model.*;
import com.springboot.milezero.repository.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        list.forEach(bookingAddonDto ->
                addonRepository.findById(bookingAddonDto.addonId())
                .orElseThrow(() -> new ResourceNotFoundException("Addon not found")));

        // Get booking from dto using mapper
        Booking booking = BookingReqMapper.convertDtoToEntity(bookingReqDto);

        // Attach customer, category, pickup branch, return branch to booking
        booking.setCustomer(customer);
        booking.setCategory(category);
        booking.setPickupBranch(pickupBranch);
        booking.setReturnBranch(returnBranch);

        // Add booking to db
        booking = bookingRepository.save(booking);

        // Get addon from dto using mapper
        // And attach addon(addon_id) and booking(booking_id) to bookingAddons

        List<BookingAddon> bookingAddons = new ArrayList<>();

        for(BookingAddonDto b : list){
            BookingAddon bookingAddon = BookingAddonReqMapper.convertDtoToEntity(b);
            bookingAddon.setBooking(booking);
            bookingAddon.setAddon(addonRepository.findById(b.addonId()).get());
            bookingAddonRepository.save(bookingAddon);
            bookingAddons.add(bookingAddon);
        }

        // Add bookingAddons to db
        bookingAddonRepository.saveAll(bookingAddons);
    }
}
