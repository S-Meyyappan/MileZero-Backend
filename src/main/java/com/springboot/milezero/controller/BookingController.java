package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.BookingReqDto;
import com.springboot.milezero.dto.response.BookingQuoteRespDto;
import com.springboot.milezero.dto.response.BookingRespDto;
import com.springboot.milezero.dto.response.BookingSummaryDto;
import com.springboot.milezero.enums.BookingStatus;
import com.springboot.milezero.enums.BookingType;
import com.springboot.milezero.enums.FilterSort;
import com.springboot.milezero.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/booking")
@CrossOrigin("http://localhost:5173")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/calculate-quote")
    public BookingQuoteRespDto getQuote(@Valid @RequestBody BookingReqDto bookingReqDto) {
        return bookingService.getQuote(bookingReqDto);
    }

    @PostMapping("/add")
    public BookingRespDto add(@Valid @RequestBody BookingReqDto bookingReqDto) {
        return bookingService.add(bookingReqDto);
    }

    @GetMapping("/get/{id}")
    public BookingRespDto get(@PathVariable long id){
        return bookingService.get(id);
    }

    @GetMapping("/get-by-branch/{id}")
    public List<BookingRespDto> getByBranch(@PathVariable long id){
        return bookingService.getByBranch(id);
    }

    @GetMapping("/get-by-vehicle/{id}")
    public List<BookingRespDto> getByVehicle(@PathVariable long id){
        return bookingService.getByVehicle(id);
    }

    @GetMapping("/get-by-customer/me")
    public Page<BookingRespDto> getByCustomer(Principal principal,
                                              @RequestParam(required = false) String search,
                                              @RequestParam(required = false) BookingType bookingType,
                                              @RequestParam(required = false) BookingStatus bookingStatus,
                                              @RequestParam(required = false) String category,
                                              @RequestParam(required = false) Instant startDate,
                                              @RequestParam(required = false) Instant endDate,
                                              @RequestParam(required = false, defaultValue = "0") int page,
                                              @RequestParam(required = false, defaultValue = "8") int size,
                                              @RequestParam(required = false, defaultValue = "DEFAULT") FilterSort filterSort){
        String email = principal.getName();
        return bookingService.getByCustomer(email, search, bookingType, bookingStatus, category, startDate, endDate, page, size, filterSort);
    }

    @PostMapping("/add/me")
    public BookingRespDto postByLoggedInCustomer(Principal principal,
                                                 @Valid @RequestBody BookingReqDto bookingReqDto) {
        String email = principal.getName();
        return bookingService.postByLoggedInCustomer(email, bookingReqDto);
    }

    @GetMapping("/get-by-branch/me")
    public Page<BookingRespDto> getByBranchLoggedInEmp(Principal principal,
                                              @RequestParam(required = false) String search,
                                              @RequestParam(required = false) BookingType bookingType,
                                              @RequestParam(required = false) BookingStatus bookingStatus,
                                              @RequestParam(required = false) String category,
                                              @RequestParam(required = false) Instant startDate,
                                              @RequestParam(required = false) Instant endDate,
                                              @RequestParam(required = false, defaultValue = "0") int page,
                                              @RequestParam(required = false, defaultValue = "8") int size,
                                              @RequestParam(required = false, defaultValue = "DEFAULT") FilterSort filterSort){
        String email = principal.getName();
        return bookingService.getByBranchLoggedInEmp(email, search, bookingType, bookingStatus, category, startDate, endDate, page, size, filterSort);
    }

    @PutMapping("/cancel/{id}")
    public void cancelBooking(@PathVariable long id) {
        bookingService.cancelBooking(id);
    }

    @PutMapping("/change-status/no-show/{id}")
    public void changeBookingStatusNoShow (@PathVariable long id) {
        bookingService.changeBookingStatus(id);
    }

    @GetMapping("/get/check-pickuplog/{id}")
    public BookingRespDto getWithCheckPickuplog(@PathVariable long id){
        return bookingService.getWithCheckPickuplog(id);
    }

    @GetMapping("/get/check-returnlog/{id}")
    public BookingRespDto getWithCheckReturnlog(@PathVariable long id){
        return bookingService.getWithCheckReturnlog(id);
    }

    @GetMapping("/get-summary/{id}")
    public BookingSummaryDto getBookingSummary(@PathVariable long id){
        return bookingService.getBookingSummary(id);
    }

}
