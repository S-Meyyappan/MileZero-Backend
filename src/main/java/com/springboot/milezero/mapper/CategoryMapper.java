package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.request.CategoryReqDto;
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
}
