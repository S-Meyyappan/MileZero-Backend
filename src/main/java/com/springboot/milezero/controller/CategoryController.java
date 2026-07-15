package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.CategoryReqDto;
import com.springboot.milezero.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/add")
    public void add(@Valid @RequestBody CategoryReqDto categoryreqDto){
        categoryService.add(categoryreqDto);
    }
}
