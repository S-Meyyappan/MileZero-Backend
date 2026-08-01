package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.request.CategoryReqDto;
import com.springboot.milezero.dto.response.CategoryRespDto;
import com.springboot.milezero.model.Category;

public class CategoryMapper {

    private CategoryMapper() {
        /* This utility class should not be instantiated */
    }

    public static Category convertDtoToEntity(CategoryReqDto dto){
        Category category = new Category();
        category.setName(dto.name());
        category.setBasePricePerDay(dto.basePricePerDay());
        category.setBasePricePerKm(dto.basePricePerKm());
        category.setBasePricePerHour(dto.basePricePerHour());
        category.setIncludedKmPerDay(dto.includedKmPerDay());
        return category;
    }

    public static CategoryRespDto convertEntityToDto(Category category){
        return new CategoryRespDto(
                category.getId(),
                category.getName(),
                category.getBasePricePerDay(),
                category.getBasePricePerHour(),
                category.getBasePricePerKm(),
                category.getIncludedKmPerDay()
        );
    }
}
