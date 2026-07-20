package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.BookingReqDto;
import com.springboot.milezero.dto.response.BookingQuoteRespDto;
import com.springboot.milezero.dto.response.BookingRespDto;
import com.springboot.milezero.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/calculate-quote")
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

    @GetMapping("/get-by-customer")
    public List<BookingRespDto> getByCustomer(@RequestParam String customerName){
        return bookingService.getByCustomer(customerName);
    }

}
