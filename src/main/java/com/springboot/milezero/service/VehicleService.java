package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.VehicleReqDto;
import com.springboot.milezero.dto.response.VehicleRespDto;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.mapper.VehicleMapper;
import com.springboot.milezero.model.Branch;
import com.springboot.milezero.model.Category;
import com.springboot.milezero.model.Vehicle;
import com.springboot.milezero.repository.BranchRepository;
import com.springboot.milezero.repository.CategoryRepository;
import com.springboot.milezero.repository.VehicleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

        Vehicle vehicle = VehicleMapper.convertDtoToEntity(vehicleReqDto);

        vehicle.setCategory(category);
        vehicle.setBranch(branch);

        vehicleRepository.save(vehicle);
    }

    public VehicleRespDto get(long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle Id Invalid"));

        return VehicleMapper.convertEntityToDto(vehicle);
    }

    public VehicleRespDto getByCategory(long id) {
        Vehicle vehicle = vehicleRepository.findByCategoryId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle Id Invalid"));

        return VehicleMapper.convertEntityToDto(vehicle);
    }

    public List<VehicleRespDto> getAll() {
        List<Vehicle> list = vehicleRepository.findAll();

        return list.
                stream()
                .map(VehicleMapper::convertEntityToDto)
                .toList();
    }

    public void update(@Valid VehicleReqDto dto, long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle Id Invalid"));

        vehicle.setRegistrationNo(dto.registrationNo());
        vehicle.setChassisNo(dto.chassisNo());
        vehicle.setManufacturer(dto.manufacturer());
        vehicle.setModel(dto.model());
        vehicle.setManufacturingYear(dto.manufacturingYear());
        vehicle.setFuelType(dto.fuelType());
        vehicle.setTransmission(dto.transmission());
        vehicle.setDriveType(dto.driveType());
        vehicle.setCurrentOdometer(dto.currentOdometer());
        vehicle.setPurchaseDate(dto.purchaseDate());
        vehicle.setWithAc(dto.withAc());

        vehicleRepository.save(vehicle);
    }
}
