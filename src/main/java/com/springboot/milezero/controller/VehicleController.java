package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.VehicleReqDto;
import com.springboot.milezero.dto.response.VehicleRespDto;
import com.springboot.milezero.enums.FilterSort;
import com.springboot.milezero.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get-by-branch/{id}")
    public List<VehicleRespDto> getByBranch(@PathVariable long id){
        return vehicleService.getAllByBranch(id);
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
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "8") int size,
            @RequestParam(required = false, defaultValue = "DEFAULT") FilterSort filterSort){

        return vehicleService.getAll(branchId, search, brand, fuelType, transmission, seats, category, page, size, filterSort);
    }

    @PutMapping("/update/{id}")
    public VehicleRespDto update( @PathVariable long id,
                        @Valid @RequestBody VehicleReqDto vehicleReqDto){
        return vehicleService.update(vehicleReqDto, id);
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
