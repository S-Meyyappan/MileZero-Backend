package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.AvailableVehicleReqDto;
import com.springboot.milezero.dto.response.AvailabileVehicleRespDto;
import com.springboot.milezero.service.AvailabilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @GetMapping("/all-vehicles")
    public List<AvailabileVehicleRespDto> getAllVehicles(@Valid @RequestBody AvailableVehicleReqDto availableVehicleReqDto){
        return availabilityService.getAllVehicles(availableVehicleReqDto);
    }
}
