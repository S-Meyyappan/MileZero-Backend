package com.springboot.milezero.dto.response;

import com.springboot.milezero.enums.FeatureGroup;

public record FeatureRespDto(
        long id,
        String name,
        FeatureGroup featureGroup,
        String description
) {
}
