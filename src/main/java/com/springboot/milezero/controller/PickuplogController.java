package com.springboot.milezero.controller;

import com.springboot.milezero.dto.response.PickupSummaryDto;
import com.springboot.milezero.dto.response.PickuplogRespDto;
import com.springboot.milezero.enums.BookingStatus;
import com.springboot.milezero.enums.BookingType;
import com.springboot.milezero.enums.FilterSort;
import com.springboot.milezero.enums.FuelLevel;
import com.springboot.milezero.service.PickuplogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pickuplog")
@CrossOrigin("http://localhost:5173")
public class PickuplogController {

    private final PickuplogService pickuplogService;

    @PostMapping("/add")
    public PickuplogRespDto add(Principal principal,
                                @RequestParam("odometer") long odometer,
                                @RequestParam("fuelLevel") FuelLevel fuelLevel,
                                @RequestParam(required = false, defaultValue = "") String remarks,
                                @RequestParam("bookingId") long bookingId,
                                @RequestParam(value = "files", required = false) List<MultipartFile> imageFiles ) {
        String email = principal.getName();
        return pickuplogService.add(email, odometer, fuelLevel, remarks, bookingId, imageFiles);
    }

    @GetMapping("/get-by/booking/{id}")
    public PickuplogRespDto getByBookingId(@PathVariable long id){
        return pickuplogService.getByBookingId(id);
    }


    @GetMapping("/get-all/me")
    public Page<PickupSummaryDto> getAllByEmployee(Principal principal,
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
        return pickuplogService.getAllByEmployee(email, search, bookingType, bookingStatus, category, startDate, endDate, page, size, filterSort);
    }

}
