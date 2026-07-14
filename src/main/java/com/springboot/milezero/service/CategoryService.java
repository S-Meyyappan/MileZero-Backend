package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.CategoryreqDto;
import com.springboot.milezero.mapper.request.CategoryReqMapper;
import com.springboot.milezero.model.Category;
import com.springboot.milezero.repository.CategoryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void add(CategoryreqDto categoryreqDto) {
        Category category = CategoryReqMapper.convertDtoToEntity(categoryreqDto);
        categoryRepository.save(category);
    }
}
