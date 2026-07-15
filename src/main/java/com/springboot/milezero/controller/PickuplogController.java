package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.PickuplogReqDto;
import com.springboot.milezero.service.PickuplogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pickuplog")
public class PickuplogController {

    private final PickuplogService pickuplogService;

    @PostMapping("/add")
    public void add(@Valid @RequestBody PickuplogReqDto pickuplogReqDto) {
        pickuplogService.add(pickuplogReqDto);
    }
}
