package com.springboot.milezero.utility;

import com.springboot.milezero.dto.request.ReturnlogReqDto;
import com.springboot.milezero.model.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;

@RequiredArgsConstructor
@Component
public class ReturnPricingUtil {

    private final BookingPricingUtil bookingPricingUtil;

    public BigDecimal calculateExtraCharges(ReturnlogReqDto dto, Booking booking){
        // Calculate extraKm
        long drivenKm = dto.odometer() - booking.getVehicle().getCurrentOdometer();
        long diffKm = drivenKm - booking.getIncludedKm();

        int extraKm =  diffKm <= 0 ? 0 : (int) diffKm;

        System.out.println("Extra km : "+extraKm);

        // Calculate late days or hours based on booking type
        Duration duration = Duration.between(booking.getPlannedReturn(), dto.actualReturn());
        BigDecimal days = DurationCalcUtil.getDurationOfDays(duration);
        BigDecimal hours = DurationCalcUtil.getDurationOfHours(duration);

        // If return is late more than day calc on basis of day else on basis of hours
        BigDecimal extraBaseFee = days.compareTo(BigDecimal.ONE) > 0
                ? bookingPricingUtil.calculateBasePriceForDaily(booking.getCategory(), extraKm, days)
                : bookingPricingUtil.calculateBasePriceForHourly(booking.getCategory(), extraKm, hours);

        // return extra fee
        return extraBaseFee;
    }
}
