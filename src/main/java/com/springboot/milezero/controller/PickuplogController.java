package com.springboot.milezero.controller;

import com.springboot.milezero.dto.response.PickuplogRespDto;
import com.springboot.milezero.enums.FuelLevel;
import com.springboot.milezero.service.PickuplogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
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
                                @RequestParam("vehicleId") long vehicleId,
                                @RequestParam("bookingId") long bookingId,
                                @RequestParam(value = "files", required = false) List<MultipartFile> imageFiles ) {
        String email = principal.getName();
        return pickuplogService.add(email, odometer, fuelLevel, remarks, vehicleId, bookingId, imageFiles);
    }

    @GetMapping("/get-by/booking/{id}")
    public PickuplogRespDto getByBookingId(@PathVariable long id){
        return pickuplogService.getByBookingId(id);
    }


}
