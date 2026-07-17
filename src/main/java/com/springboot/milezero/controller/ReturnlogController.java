package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.ReturnlogReqDto;
import com.springboot.milezero.service.ReturnlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/returnlog")
public class ReturnlogController {

    private final ReturnlogService returnlogService;

    @PostMapping("/add")
    public void add(@Valid @RequestBody ReturnlogReqDto returnlogReqDto){
        returnlogService.add(returnlogReqDto);
    }

    @GetMapping("/calc")
    public BigDecimal calc(@Valid @RequestBody ReturnlogReqDto returnlogReqDto){
        return returnlogService.calc(returnlogReqDto);
    }
}
