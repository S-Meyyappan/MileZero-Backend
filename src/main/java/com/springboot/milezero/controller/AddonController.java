package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.AddonReqDto;
import com.springboot.milezero.service.AddonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/addon")
public class AddonController {

    private final AddonService addonService;

    @PostMapping("/add")
    public void add(@Valid @RequestBody AddonReqDto addonReqDto) {
        addonService.add(addonReqDto);
    }
}
