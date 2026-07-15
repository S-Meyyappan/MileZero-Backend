package com.springboot.milezero.utility;

import com.springboot.milezero.dto.request.BookingAddonDto;
import com.springboot.milezero.model.Category;
import com.springboot.milezero.service.AddonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingPricingUtil {

    private final AddonService addonService;

    public BigDecimal calculateBasePriceForHourly(Category category, int totalKm, Duration duration) {
        BigDecimal hours = new BigDecimal(duration.toHours());
        BigDecimal hoursFee = category.getBasePricePerHour().multiply(hours);
        BigDecimal kmFee = category.getBasePricePerKm().multiply(new BigDecimal(totalKm));
        return hoursFee.add(kmFee);
    }

    public BigDecimal calculateBasePriceForDaily(Category category, int totalKm, Duration duration) {
        BigDecimal days = new BigDecimal(duration.toDays());
        BigDecimal daysFee = category.getBasePricePerDay().multiply(days);
        BigDecimal kmFee = category.getBasePricePerKm().multiply(new BigDecimal(totalKm));
        return daysFee.add(kmFee);
    }

    public BigDecimal calculateAddonPrice(List<BookingAddonDto> list, long days) {
        return list.stream()
                .map(dto -> {
                BigDecimal price = addonService.get(dto.addonId()).getPricePerDay();
                BigDecimal addonPriceOfQty = price.multiply(new BigDecimal(dto.quantity()));
                return addonPriceOfQty.multiply(new BigDecimal(days));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
