package com.springboot.milezero.utility;

import com.springboot.milezero.model.Booking;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

@RequiredArgsConstructor
@Component
public class ReturnPricingUtil {

    private static final Logger log = LoggerFactory.getLogger(ReturnPricingUtil.class);
    private final BookingPricingUtil bookingPricingUtil;

    public BigDecimal calculateExtraCharges(Long odometer, Instant actualReturn, Booking booking){
        // Calculate extraKm
        long drivenKm = odometer - booking.getVehicle().getCurrentOdometer();
        long diffKm = drivenKm - booking.getIncludedKm();

        int extraKm =  diffKm <= 0 ? 0 : (int) diffKm;

        log.info("Driven Km: {}", drivenKm);
        log.info("Included Km: {}", booking.getIncludedKm());
        log.info("Diff Km: {}", diffKm);
        log.info("Extra Km: {}", extraKm);

        // Calculate late days or hours based on booking type
        Duration duration = Duration.between(booking.getPlannedReturn(), actualReturn);
        BigDecimal days = DurationCalcUtil.getDurationOfDays(duration);
        BigDecimal hours = DurationCalcUtil.getDurationOfHours(duration);

        log.info("Duration: {}", duration);
        log.info("Days: {}", days);
        log.info("Hours: {}", hours);

        // If return is on time return 0
        if(duration.isNegative() || duration.isZero()){
            return BigDecimal.ZERO;
        }
        // If return is late more than day calc on basis of day else on basis of hours
        BigDecimal extraBaseFee = days.compareTo(BigDecimal.ONE) >= 0
                ? bookingPricingUtil.calculateBasePriceForDaily(booking.getCategory(), extraKm, days)
                : bookingPricingUtil.calculateBasePriceForHourly(booking.getCategory(), extraKm, hours);

        log.info("Extra Base Fee: {}", extraBaseFee);

        // return extra fee
        return extraBaseFee;
    }
}
