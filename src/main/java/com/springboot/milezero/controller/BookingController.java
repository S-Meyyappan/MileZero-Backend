package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.BookingReqDto;
import com.springboot.milezero.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/add")
    public void add(@Valid @RequestBody BookingReqDto bookingReqDto) {
        bookingService.add(bookingReqDto);
    }
}
