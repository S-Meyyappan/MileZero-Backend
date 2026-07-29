package com.springboot.milezero.dto.request;

import com.springboot.milezero.enums.FeatureGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FeatureReqDto(
        @NotBlank(message = "Field Cannot be empty")
        String name,

        @NotNull(message = "Field Cannot be empty")
        FeatureGroup featureGroup,

        String description
) {
}
