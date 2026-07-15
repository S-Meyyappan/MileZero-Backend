package com.springboot.milezero.dto.util;

import com.springboot.milezero.model.Addon;

public record AddonDtoUtil(
        Addon addon,
        int quantity
) {
}
