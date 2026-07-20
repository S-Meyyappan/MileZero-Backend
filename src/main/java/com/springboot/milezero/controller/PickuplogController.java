package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.PickuplogReqDto;
import com.springboot.milezero.dto.response.PickuplogRespDto;
import com.springboot.milezero.service.PickuplogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pickuplog")
public class PickuplogController {

    private final PickuplogService pickuplogService;

    @PostMapping("/add")
    public PickuplogRespDto add(@Valid @RequestBody PickuplogReqDto pickuplogReqDto) {
        return pickuplogService.add(pickuplogReqDto);
    }

    @GetMapping("/get-by/booking/{id}")
    public PickuplogRespDto getByBookingId(@PathVariable long id){
        return pickuplogService.getByBookingId(id);
    }


}
