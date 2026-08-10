package com.springboot.milezero.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

public class DurationCalcUtil {
    private DurationCalcUtil() {
        /* This utility class should not be instantiated */
    }


    public static BigDecimal getDurationOfDays(Duration duration) {
        BigDecimal days = BigDecimal.valueOf(duration.toDaysPart());
        BigDecimal hours = BigDecimal.valueOf(duration.toHoursPart());
        BigDecimal fractionalDays = hours.divide(new BigDecimal(24), 2, RoundingMode.CEILING);
        return days.add(fractionalDays);
    }

    public static BigDecimal getDurationOfHours(Duration duration) {
        BigDecimal hours = BigDecimal.valueOf(duration.toHours());
        BigDecimal minutes = BigDecimal.valueOf(duration.toMinutesPart());
        BigDecimal fractionalHours = minutes.divide(new BigDecimal(60), 2, RoundingMode.CEILING);
        return hours.add(fractionalHours);
    }
}
