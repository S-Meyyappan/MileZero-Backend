package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.BranchAvailabliltyReqDto;
import com.springboot.milezero.dto.request.GlobalAvailabilityReqDto;
import com.springboot.milezero.dto.response.AvailabileVehicleRespDto;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.repository.BranchRepository;
import com.springboot.milezero.repository.VehicleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final BranchRepository branchRepository;
    private final VehicleRepository vehicleRepository;

    public List<AvailabileVehicleRespDto> getAllVehicles(GlobalAvailabilityReqDto dto) {
        List<AvailabileVehicleRespDto> result = vehicleRepository.getAllAvailableVehicles(
                dto.chosenPickup(),
                dto.chosenReturn()
        );
        return result;
    }

    public List<AvailabileVehicleRespDto> getAllVehiclesByBranch(@Valid BranchAvailabliltyReqDto dto) {
        branchRepository.findById(dto.branchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch Id Invalid"));

        List<AvailabileVehicleRespDto> result = vehicleRepository.getAllAvailableVehiclesByBranch(
                dto.chosenPickup(),
                dto.chosenReturn(),
                dto.branchId()
        );
        return result;
    }
}
