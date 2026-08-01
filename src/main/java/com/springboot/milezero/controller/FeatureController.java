package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.FeatureReqDto;
import com.springboot.milezero.enums.FeatureGroup;
import com.springboot.milezero.model.Feature;
import com.springboot.milezero.service.FeatureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feature")
@RequiredArgsConstructor
public class FeatureController {

    private final FeatureService featureService;

    @PostMapping("/add")
    public Feature add(@Valid@ RequestBody FeatureReqDto featureReqDto){
        return featureService.add(featureReqDto);
    }

    @GetMapping("/get-all")
    public List<Feature> getAll(){
        return featureService.getAll();
    }

    @GetMapping("/get-by/{featureGroup}")
    public List<Feature> getAllByFeatureGroup(@PathVariable FeatureGroup featureGroup){
        return featureService.getAllByFeatureGroup(featureGroup);
    }
}
