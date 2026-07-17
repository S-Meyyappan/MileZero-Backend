package com.springboot.milezero.utility;

import com.springboot.milezero.dto.request.BookingAddonDto;
import com.springboot.milezero.model.Category;
import com.springboot.milezero.service.AddonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingPricingUtil {

    private final AddonService addonService;

    public BigDecimal calculateBasePriceForHourly(Category category, int requestedKm, BigDecimal hours) {
        BigDecimal hoursFee = category.getBasePricePerHour().multiply(hours);
        BigDecimal kmFee = calculateKmPrice(category, requestedKm);
        return hoursFee.add(kmFee);
    }

    public BigDecimal calculateBasePriceForDaily(Category category, int requestedKm, BigDecimal days) {
        BigDecimal daysFee = category.getBasePricePerDay().multiply(days);
        BigDecimal kmFee = calculateKmPrice(category, requestedKm);
        return daysFee.add(kmFee);
    }

    public BigDecimal calculateAddonPrice(List<BookingAddonDto> list, BigDecimal days) {
        return list.stream()
                .map(dto -> {
                BigDecimal price = addonService.get(dto.addonId()).pricePerDay();
                BigDecimal addonPriceOfQty = price.multiply(new BigDecimal(dto.quantity()));
                return addonPriceOfQty.multiply(days);
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateKmPrice(Category category, int requestedKm) {
        return category.getBasePricePerKm().multiply(new BigDecimal(requestedKm));
    }
}
