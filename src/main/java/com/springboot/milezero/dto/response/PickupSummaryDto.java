package com.springboot.milezero.dto.response;

import java.util.List;

public record PickupSummaryDto(
        BookingDashboardDto booking,
        PickuplogRespDto pickuplog,
        List<String> pickupImages
) {
}
