package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.BranchReqDto;
import com.springboot.milezero.dto.response.BranchRespDto;
import com.springboot.milezero.service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branch")
public class BranchController {

    private final BranchService branchService;
    /*
    Body:{
        name: "",
        city: "",
        address: "",
        phone: "",
    }
     */
    @PostMapping("/add")
    public void add(@Valid @RequestBody BranchReqDto branchReqDto){
        branchService.add(branchReqDto);
    }

    @GetMapping("/get-all")
    public List<BranchRespDto> getAll() {
        return branchService.getAll();
    }
}
