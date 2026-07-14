package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.VehicleReqDto;
import com.springboot.milezero.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping("/add")
    public void add(@Valid @RequestBody VehicleReqDto vehicleReqDto) {
        vehicleService.add(vehicleReqDto);
    }
}
