package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.BranchAvailabliltyReqDto;
import com.springboot.milezero.dto.request.GlobalAvailabilityReqDto;
import com.springboot.milezero.dto.response.CategoryAvailabilityRespDto;
import com.springboot.milezero.dto.response.VehicleDto;
import com.springboot.milezero.service.AvailabilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/availability")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @PostMapping("/all-vehicles")
    public List<CategoryAvailabilityRespDto> getAllVehicles(@Valid @RequestBody GlobalAvailabilityReqDto globalAvailabilityReqDto){
        return availabilityService.getAllVehicles(globalAvailabilityReqDto);
    }

    @PostMapping("/all-vehicles-by-branch")
    public List<CategoryAvailabilityRespDto> getAllVehiclesByBranch(@Valid @RequestBody BranchAvailabliltyReqDto branchAvailabliltyReqDto){
        return availabilityService.getAllVehiclesByBranch(branchAvailabliltyReqDto);
    }

    @PostMapping("/all-available-vehicles")
    public Page<VehicleDto> getAllAvailableVehicles(@Valid @RequestBody BranchAvailabliltyReqDto branchAvailabliltyReqDto,
                                                    @RequestParam(required = false, defaultValue = "0") int page,
                                                    @RequestParam(required = false, defaultValue = "8") int size){
        return availabilityService.getAllAvailableVehicles(branchAvailabliltyReqDto, page, size);
    }

    @PostMapping("/all-available-vehicles-by-category/{id}")
    public Page<VehicleDto> getAllAvailableVehicles(@Valid @RequestBody BranchAvailabliltyReqDto branchAvailabliltyReqDto,
                                                    @PathVariable long id,
                                                    @RequestParam(required = false, defaultValue = "0") int page,
                                                    @RequestParam(required = false, defaultValue = "8") int size){
        return availabilityService.getAllAvailableVehiclesByCategory(branchAvailabliltyReqDto, id, page, size);
    }
}
