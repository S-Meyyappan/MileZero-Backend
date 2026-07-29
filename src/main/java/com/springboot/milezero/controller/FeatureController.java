package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.FeatureReqDto;
import com.springboot.milezero.dto.response.FeatureRespDto;
import com.springboot.milezero.service.FeatureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feature")
@RequiredArgsConstructor
public class FeatureController {

    private final FeatureService featureService;

    @PostMapping("/add")
    public FeatureRespDto add(@Valid@ RequestBody FeatureReqDto featureReqDto){
        return featureService.add(featureReqDto);
    }
}
