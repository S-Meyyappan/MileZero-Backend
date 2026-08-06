package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.VehicleReqDto;
import com.springboot.milezero.dto.response.VehicleRespDto;
import com.springboot.milezero.enums.FilterSort;
import com.springboot.milezero.enums.FuelType;
import com.springboot.milezero.enums.Transmission;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.mapper.VehicleFeatureMapper;
import com.springboot.milezero.mapper.VehicleMapper;
import com.springboot.milezero.model.*;
import com.springboot.milezero.repository.*;
import com.springboot.milezero.utility.PageableVehicleUtil;
import com.springboot.milezero.utility.VehicleFilterSpecification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CategoryRepository categoryRepository;
    private final BranchRepository branchRepository;
    private final FeatureRepository featureRepository;
    private final VehicleFeatureRepository vehicleFeatureRepository;
    private final EmployeeRepository employeeRepository;

    public VehicleRespDto add(VehicleReqDto vehicleReqDto) {
        Category category = categoryRepository.findById(vehicleReqDto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Branch branch = branchRepository.findById(vehicleReqDto.branchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));

        List<Feature> features = featureRepository.findAllById(vehicleReqDto.featureList());

        if(features.size() != vehicleReqDto.featureList().size()){
            throw new ResourceNotFoundException("Some features not found");
        }

        Vehicle vehicle = VehicleMapper.convertDtoToEntity(vehicleReqDto);

        vehicle.setCategory(category);
        vehicle.setBranch(branch);

        Vehicle finalVehicle = vehicleRepository.save(vehicle);

        List<VehicleFeature> vehicleFeatures = features.stream()
                .map(f -> VehicleFeatureMapper.convertEntityToEntity(f,finalVehicle))
                .toList();

        vehicleFeatureRepository.saveAll(vehicleFeatures);

        List<Feature> featuresList = vehicleFeatureRepository.getAllFeatures(finalVehicle.getId());

        return VehicleMapper.convertEntityToDto(finalVehicle, featuresList);
    }

    public VehicleRespDto get(long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle Id Invalid"));

        List<Feature> featuresList = vehicleFeatureRepository.getAllFeatures(id);

        return VehicleMapper.convertEntityToDto(vehicle, featuresList);
    }

    public List<VehicleRespDto> getAllByCategory(long id) {
         List<Vehicle> vehicles = vehicleRepository.getAllByCategory(id);

         // Collect All Ids of Vehicles
         List<Long> vehicleIds = vehicles.stream()
                                    .map(Vehicle::getId)
                                    .toList();

         // Fetch All VehicleFeatures of All Vehicles in vehicleIds
         List<VehicleFeature> vehicleFeatures = vehicleFeatureRepository.getAllByVehicleId(vehicleIds);

         // Group All VehicleFeatures by Vehicle Id
         Map<Long, List<Feature>> vehicleFeatureMap = vehicleFeatures.stream()
                .collect(Collectors.groupingBy(
                        vf -> vf.getVehicle().getId(),
                        // 1. The Key
                        // Instead of storing the VehicleFeature,  we extract, collect and store the Feature List
                        // 2. The Value & 3. Container
                        Collectors.mapping(VehicleFeature::getFeature, Collectors.toList())
                ));

         return vehicles.stream()
                .map(v -> VehicleMapper.convertEntityToDto(v, vehicleFeatureMap.get(v.getId())))
                .toList();
    }


    public Page<VehicleRespDto> getAllByBranch(String email, String search, String brand, String fuelType, String transmission, Integer seats, String category, int page, int size, FilterSort filterSort) {
        Employee employee = employeeRepository.fetchByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Invalid"));

        Pageable pageable = PageableVehicleUtil.getPageable(page, size, filterSort);

        Specification<Vehicle> spec = VehicleFilterSpecification.getFilteredVehicles(
                                        employee.getBranch().getId(),
                                        search, brand, fuelType, transmission, seats, category);

        Page<Vehicle> vehicles = vehicleRepository.findAll(spec, pageable);

        List<Long> vehicleIds = vehicles.stream()
                .map(Vehicle::getId)
                .toList();

        List<VehicleFeature> vehicleFeatures = vehicleFeatureRepository.getAllByVehicleId(vehicleIds);

        Map<Long, List<Feature>> map = vehicleFeatures.stream()
                .collect(Collectors.groupingBy(
                        vf -> vf.getVehicle().getId(),
                        Collectors.mapping(VehicleFeature::getFeature, Collectors.toList())
                ));

        return vehicles
                .map(v ->
                        VehicleMapper.convertEntityToDto(
                                v,  map.get(v.getId())
                        ));
    }

    public Page<VehicleRespDto> getAll(
                                Long branchId,
                                String search,
                                String brand,
                                String fuelType,
                                String transmission,
                                Integer seats,
                                String category,
                                int page,
                                int size,
                                FilterSort filterSort) {

        // 1. Get Pageable
        Pageable pageable = PageableVehicleUtil.getPageable(page, size, filterSort);

        // 4. Apply Specification Criteria API
        Specification<Vehicle> spec = VehicleFilterSpecification.getFilteredVehicles(
                branchId, search, brand, fuelType, transmission, seats, category);

        // 5. Get Pageable Vehicles
        Page<Vehicle> vehicles = vehicleRepository.findAll(spec, pageable);

        List<Long> vehicleIds = vehicles.stream()
                .map(Vehicle::getId)
                .toList();

        List<VehicleFeature> vehicleFeatures = vehicleFeatureRepository.getAllByVehicleId(vehicleIds);

        Map<Long, List<Feature>> vehicleFeatureMap = vehicleFeatures.stream()
                .collect(Collectors.groupingBy(
                        vf -> vf.getVehicle().getId(),
                        Collectors.mapping(VehicleFeature::getFeature, Collectors.toList())
                ));

        return vehicles
                .map(v ->
                        VehicleMapper.convertEntityToDto(
                                v, vehicleFeatureMap.get(v.getId())
                        ));
    }

    public VehicleRespDto update(@Valid VehicleReqDto dto, long id) {
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
        vehicle.setSeatCount(dto.seatCount());
        vehicle.setLuggageCapacity(dto.luggageCapacity());

        vehicle = vehicleRepository.save(vehicle);

        List<Feature> featuresList = vehicleFeatureRepository.getAllFeatures(id);

        return VehicleMapper.convertEntityToDto(vehicle, featuresList);
    }

    public List<String> getAllBrands() {
        return vehicleRepository.getAllBrands();
    }

    public List<String> getAllFuelTypes() {
        return Arrays.stream(FuelType.values())
                .map(FuelType::name)
                .toList();
    }

    public List<String> getAllTransmissions() {
        return Arrays.stream(Transmission.values())
                .map(Transmission::name)
                .toList();
    }

    public List<Integer> getAllSeats() {
        return vehicleRepository.getAllSeats();
    }
}
