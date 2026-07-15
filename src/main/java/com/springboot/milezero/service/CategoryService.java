package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.CategoryReqDto;
import com.springboot.milezero.mapper.CategoryMapper;
import com.springboot.milezero.model.Category;
import com.springboot.milezero.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void add(CategoryReqDto categoryreqDto) {
        Category category = CategoryMapper.convertDtoToEntity(categoryreqDto);
        categoryRepository.save(category);
    }
}
