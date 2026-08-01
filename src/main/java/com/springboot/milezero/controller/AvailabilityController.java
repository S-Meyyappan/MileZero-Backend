package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.BranchAvailabliltyReqDto;
import com.springboot.milezero.dto.request.GlobalAvailabilityReqDto;
import com.springboot.milezero.dto.response.CategoryAvailabilityRespDto;
import com.springboot.milezero.dto.response.VehicleDto;
import com.springboot.milezero.service.AvailabilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @GetMapping("/all-vehicles")
    public List<CategoryAvailabilityRespDto> getAllVehicles(@Valid @RequestBody GlobalAvailabilityReqDto globalAvailabilityReqDto){
        return availabilityService.getAllVehicles(globalAvailabilityReqDto);
    }

    @GetMapping("/all-vehicles-by-branch")
    public List<CategoryAvailabilityRespDto> getAllVehiclesByBranch(@Valid @RequestBody BranchAvailabliltyReqDto branchAvailabliltyReqDto){
        return availabilityService.getAllVehiclesByBranch(branchAvailabliltyReqDto);
    }

    @GetMapping("/all-available-vehicles")
    public List<VehicleDto> getAllAvailableVehicles(@Valid @RequestBody BranchAvailabliltyReqDto branchAvailabliltyReqDto){
        return availabilityService.getAllAvailableVehicles(branchAvailabliltyReqDto);
    }

    @GetMapping("/all-available-vehicles-by-category/{id}")
    public List<VehicleDto> getAllAvailableVehicles(@Valid @RequestBody BranchAvailabliltyReqDto branchAvailabliltyReqDto,
                                                    @PathVariable long id){
        return availabilityService.getAllAvailableVehiclesByCategory(branchAvailabliltyReqDto, id);
    }
}
