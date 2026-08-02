package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.BranchAvailabliltyReqDto;
import com.springboot.milezero.dto.request.GlobalAvailabilityReqDto;
import com.springboot.milezero.dto.response.CategoryAvailabilityRespDto;
import com.springboot.milezero.dto.response.VehicleDto;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.mapper.VehicleMapper;
import com.springboot.milezero.model.Vehicle;
import com.springboot.milezero.repository.BranchRepository;
import com.springboot.milezero.repository.VehicleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final BranchRepository branchRepository;
    private final VehicleRepository vehicleRepository;

    public List<CategoryAvailabilityRespDto> getAllVehicles(GlobalAvailabilityReqDto dto) {
        return vehicleRepository.getAllAvailableVehicles(
                dto.chosenPickup(),
                dto.chosenReturn()
        );
    }

    public List<CategoryAvailabilityRespDto> getAllVehiclesByBranch(@Valid BranchAvailabliltyReqDto dto) {
        branchRepository.findById(dto.branchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch Id Invalid"));

        return vehicleRepository.getAllAvailableVehiclesByBranch(
                dto.chosenPickup(),
                dto.chosenReturn(),
                dto.branchId()
        );
    }

    public Page<VehicleDto> getAllAvailableVehicles(@Valid BranchAvailabliltyReqDto branchAvailabliltyReqDto, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Vehicle> list = vehicleRepository.getVehicleByAvailability(
                branchAvailabliltyReqDto.chosenPickup(),
                branchAvailabliltyReqDto.chosenReturn(),
                branchAvailabliltyReqDto.branchId(),
                pageable
        );

        return list.map(VehicleMapper::convertEntityToDto);
    }

    public Page<VehicleDto> getAllAvailableVehiclesByCategory(BranchAvailabliltyReqDto branchAvailabliltyReqDto,
                                                              long id,
                                                              int page,
                                                              int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Vehicle> list = vehicleRepository.getAllAvailableVehiclesByCategory(
                branchAvailabliltyReqDto.chosenPickup(),
                branchAvailabliltyReqDto.chosenReturn(),
                branchAvailabliltyReqDto.branchId(),
                id,
                pageable);

        return list.map(VehicleMapper::convertEntityToDto);
    }
}
