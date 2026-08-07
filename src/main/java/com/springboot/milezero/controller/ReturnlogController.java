package com.springboot.milezero.controller;

import com.springboot.milezero.dto.response.ReturnlogRespDto;
import com.springboot.milezero.enums.FuelLevel;
import com.springboot.milezero.service.ReturnlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/returnlog")
@CrossOrigin("http://localhost:5173")
public class ReturnlogController {

    private final ReturnlogService returnlogService;

    @PostMapping("/add")
    public ReturnlogRespDto add(Principal principal,
                                @RequestParam("odometer") long odometer,
                                @RequestParam("fuelLevel") FuelLevel fuelLevel,
                                @RequestParam(required = false, defaultValue = "") String remarks,
                                @RequestParam("bookingId") long bookingId,
                                @RequestParam(value = "files", required = false) List<MultipartFile> imageFiles) {
        String email = principal.getName();
        return returnlogService.add(email, odometer, fuelLevel, remarks, bookingId, imageFiles);
    }

//    @GetMapping("/calc")
//    public BigDecimal calc(@Valid @RequestBody ReturnlogReqDto returnlogReqDto){
//        return returnlogService.calc(returnlogReqDto);
//    }

//    @GetMapping("/get-by/booking/{id}")
//    public ReturnlogRespDto getByBookinId(@PathVariable long id){
//        return returnlogService.getByBookingId(id);
//    }

}
