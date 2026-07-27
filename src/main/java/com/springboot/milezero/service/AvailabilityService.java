package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.AvailableVehicleReqDto;
import com.springboot.milezero.dto.response.AvailabileVehicleRespDto;
import com.springboot.milezero.repository.BranchRepository;
import com.springboot.milezero.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final BranchRepository branchRepository;
    private final VehicleRepository vehicleRepository;

    public List<AvailabileVehicleRespDto> getAllVehicles(AvailableVehicleReqDto dto) {
        List<AvailabileVehicleRespDto> result = vehicleRepository.getAllAvailableVehicles(
                dto.chosenPickup(),
                dto.chosenReturn()
        );
        return result;
    }
}
