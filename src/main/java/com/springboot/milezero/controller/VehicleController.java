package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.VehicleReqDto;
import com.springboot.milezero.dto.response.VehicleRespDto;
import com.springboot.milezero.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
@RequiredArgsConstructor
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

    @GetMapping("/get-all")
    public List<VehicleRespDto> getALl(){
        return vehicleService.getAll();
    }

    @PutMapping("/update/{id}")
    public VehicleRespDto update( @PathVariable long id,
                        @Valid @RequestBody VehicleReqDto vehicleReqDto){
        return vehicleService.update(vehicleReqDto, id);
    }
}
