package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.VehicleReqDto;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.mapper.request.VehicleReqMapper;
import com.springboot.milezero.model.Branch;
import com.springboot.milezero.model.Category;
import com.springboot.milezero.model.Vehicle;
import com.springboot.milezero.repository.BranchRepository;
import com.springboot.milezero.repository.CategoryRepository;
import com.springboot.milezero.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CategoryRepository categoryRepository;
    private final BranchRepository branchRepository;

    public void add(VehicleReqDto vehicleReqDto) {
        Category category = categoryRepository.findById(vehicleReqDto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Branch branch = branchRepository.findById(vehicleReqDto.branchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));

        Vehicle vehicle = VehicleReqMapper.convertDtoToEntity(vehicleReqDto);

        vehicle.setCategory(category);
        vehicle.setBranch(branch);

        vehicleRepository.save(vehicle);
    }
}
