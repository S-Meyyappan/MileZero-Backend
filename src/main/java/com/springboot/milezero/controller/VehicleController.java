package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.VehicleReqDto;
import com.springboot.milezero.dto.request.VehicleUpdateDto;
import com.springboot.milezero.dto.response.VehicleRespDto;
import com.springboot.milezero.enums.FilterSort;
import com.springboot.milezero.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping("/add")
    public VehicleRespDto add(@Valid @RequestBody VehicleReqDto vehicleReqDto) {
        return vehicleService.add(vehicleReqDto);
    }

    @GetMapping("/get/{id}")
    public VehicleRespDto get(@PathVariable long id){
        return vehicleService.get(id);
    }

    @GetMapping("/get-by-category/{id}")
    public List<VehicleRespDto> getByCategory(@PathVariable long id){
        return vehicleService.getAllByCategory(id);
    }

    @GetMapping("/get-by-branch/me")
    public Page<VehicleRespDto> getByBranch(Principal principal,
                                            @RequestParam(required = false) String search,
                                            @RequestParam(required = false) String brand,
                                            @RequestParam(required = false) String fuelType,
                                            @RequestParam(required = false) String transmission,
                                            @RequestParam(required = false) Integer seats,
                                            @RequestParam(required = false) String category,
                                            @RequestParam(required = false) String currentStatus,
                                            @RequestParam(required = false, defaultValue = "0") int page,
                                            @RequestParam(required = false, defaultValue = "8") int size,
                                            @RequestParam(required = false, defaultValue = "DEFAULT") FilterSort filterSort){
        String email = principal.getName();
        return vehicleService.getAllByBranch(email, search, brand, fuelType, transmission, seats, category, currentStatus, page, size, filterSort);
    }

    @GetMapping("/get-all")
    public Page<VehicleRespDto> getALl(
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String fuelType,
            @RequestParam(required = false) String transmission,
            @RequestParam(required = false) Integer seats,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String currentStatus,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "8") int size,
            @RequestParam(required = false, defaultValue = "DEFAULT") FilterSort filterSort){

        return vehicleService.getAll(branchId, search, brand, fuelType, transmission, seats, category, currentStatus, page, size, filterSort);
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable Long id,
                                 @RequestPart("vehicle") VehicleUpdateDto vehicle,
                                 @RequestPart(value = "image", required = false) MultipartFile image){
        vehicleService.update(id, vehicle, image);
    }

    @PostMapping("/add-new")
    public void update(Principal principal,
                       @RequestPart("vehicle") VehicleReqDto vehicle,
                       @RequestPart(value = "image", required = false) MultipartFile image){
        String email = principal.getName();
        vehicleService.addVehicle(email, vehicle, image);
    }

    @GetMapping("/get-all-brands")
    public List<String> getAllBrands(){
        return vehicleService.getAllBrands();
    }

    @GetMapping("/get-all-fuel-types")
    public List<String> getAllFuelTypes(){
        return vehicleService.getAllFuelTypes();
    }

    @GetMapping("/get-all-transmissions")
    public List<String> getAllTransmissions(){
        return vehicleService.getAllTransmissions();
    }

    @GetMapping("/get-all-seats")
    public List<Integer> getAllSeats(){
        return vehicleService.getAllSeats();
    }


}
