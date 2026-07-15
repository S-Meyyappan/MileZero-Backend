package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.BookingReqDto;
import com.springboot.milezero.dto.response.BookingQuoteRespDto;
import com.springboot.milezero.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public void add(@Valid @RequestBody BookingReqDto bookingReqDto) {
        bookingService.add(bookingReqDto);
    }
}
