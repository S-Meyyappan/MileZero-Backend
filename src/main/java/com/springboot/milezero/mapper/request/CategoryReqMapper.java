package com.springboot.milezero.mapper.request;

import com.springboot.milezero.dto.request.CategoryreqDto;
import com.springboot.milezero.model.Category;

public class CategoryReqMapper {

    public static Category convertDtoToEntity(CategoryreqDto dto){
        Category category = new Category();
        category.setName(dto.name());
        category.setBasePricePerDay(dto.basePricePerDay());
        category.setExtraFeePerKm(dto.extraFeePerKm());
        category.setLateFeePerHour(dto.lateFeePerHour());
        return category;
    }
}
