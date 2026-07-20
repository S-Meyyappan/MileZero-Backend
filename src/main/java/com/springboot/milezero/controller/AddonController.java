package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.AddonReqDto;
import com.springboot.milezero.dto.response.AddonRespDto;
import com.springboot.milezero.service.AddonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/addon")
public class AddonController {

    private final AddonService addonService;

    @PostMapping("/add")
    public AddonRespDto add(@Valid @RequestBody AddonReqDto addonReqDto) {
        return addonService.add(addonReqDto);
    }

    @GetMapping("/get/{id}")
    public AddonRespDto get(@PathVariable long id){
        return addonService.get(id);
    }

    @GetMapping("/get-all")
    public List<AddonRespDto> getAll(){
        return addonService.getAll();
    }

    @PutMapping("/update/{id}")
    public AddonRespDto update( @PathVariable long id,
                        @Valid @RequestBody AddonReqDto addonReqDto) {
        return addonService.update(addonReqDto,id);
    }
}
