package com.springboot.milezero.utility;

import com.springboot.milezero.enums.FilterSort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


public class PageableVehicleUtil {
    private PageableVehicleUtil() {
        /* This utility class should not be instantiated */
    }


    public static Pageable getPageable(int page, int size, FilterSort filterSort) {
        // 1. Declare Sort not sorted by default
        Sort sort;

        switch (filterSort){
            case PRICE_LOW_TO_HIGH -> sort = Sort.by("category.basePricePerDay").ascending();
            case PRICE_HIGH_TO_LOW -> sort = Sort.by("category.basePricePerDay").descending();
            case YEAR_HIGH_TO_LOW ->  sort = Sort.by("manufacturingYear").descending();
            case YEAR_LOW_TO_HIGH ->  sort = Sort.by("manufacturingYear").ascending();
            default -> sort = Sort.unsorted();
        }

        // 3. Create pageable
        return PageRequest.of(page, size, sort);
    }
}
