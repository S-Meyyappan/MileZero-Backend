package com.springboot.milezero.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BranchReqDto(
        @NotBlank(message = "Field cannot be empty")
        String name,
        @NotBlank(message = "Field cannot be empty")
        String city,
        @NotBlank(message = "Field cannot be empty")
        String address,
        @NotBlank(message = "Field cannot be empty")
        @Size(min = 10, max = 10, message = "Invalid phone number")
        String phone
) {
}
