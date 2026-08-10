package com.springboot.milezero.utility;

import com.springboot.milezero.enums.FilterSort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class BookingPageableUtil {
    private BookingPageableUtil() {
        /* This utility class should not be instantiated */
    }


    public static Pageable getPageable(int page, int size, FilterSort filterSort) {
        Sort sort;

        switch (filterSort){
            case PRICE_LOW_TO_HIGH -> sort = Sort.by("estimatedCost").ascending();
            case PRICE_HIGH_TO_LOW -> sort = Sort.by("estimatedCost").descending();
            case YEAR_HIGH_TO_LOW ->  sort = Sort.by("plannedPickup").descending();
            case YEAR_LOW_TO_HIGH ->  sort = Sort.by("plannedPickup").ascending();
            default -> sort = Sort.unsorted();
        }

        return PageRequest.of(page, size, sort);
    }
}
