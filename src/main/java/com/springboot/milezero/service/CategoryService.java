package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.CategoryReqDto;
import com.springboot.milezero.dto.response.CategoryRespDto;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.mapper.CategoryMapper;
import com.springboot.milezero.model.Category;
import com.springboot.milezero.repository.CategoryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void add(CategoryReqDto categoryreqDto) {
        Category category = CategoryMapper.convertDtoToEntity(categoryreqDto);
        categoryRepository.save(category);
    }

    public CategoryRespDto get(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category Id Invalid"));

        return CategoryMapper.convertEntityToDto(category);
    }

    public void update(@Valid CategoryReqDto dto, long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category Id Invalid"));

        category.setName(dto.name());
        category.setBasePricePerDay(dto.basePricePerDay());
        category.setBasePricePerHour(dto.basePricePerHour());
        category.setBasePricePerKm(dto.basePricePerKm());
        category.setIncludedKmPerDay(dto.includedKmPerDay());

        categoryRepository.save(category);
    }

    public List<CategoryRespDto> getAll() {
        List<Category> list = categoryRepository.findAll();

        return list
                .stream()
                .map(CategoryMapper::convertEntityToDto)
                .toList();
    }
}
