package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.CategoryReqDto;
import com.springboot.milezero.dto.response.CategoryRespDto;
import com.springboot.milezero.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
@CrossOrigin("http://localhost:5173")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/add")
    public CategoryRespDto add(@Valid @RequestBody CategoryReqDto categoryreqDto){
        return categoryService.add(categoryreqDto);
    }

    @GetMapping("/get/{id}")
    public CategoryRespDto get(@PathVariable long id){
        return categoryService.get(id);
    }

    @GetMapping("/get-all")
    public List<CategoryRespDto> getALl(){
        return categoryService.getAll();
    }

    @PutMapping("/update/{id}")
    public CategoryRespDto update( @PathVariable long id,
                        @Valid @RequestBody CategoryReqDto categoryreqDto){
        return categoryService.update(categoryreqDto, id);
    }
}
