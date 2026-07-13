package com.springboot.milezero.dto.request;

import com.springboot.milezero.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CustomerReqDto(
        @NotBlank (message = "Field cannot be null")
        String name,

        @NotBlank (message = "Field cannot be null")
        String phone,

        @NotBlank (message = "Field cannot be null")
        String licenseNo,

        @NotNull(message = "Field cannot be null")
        LocalDate licenseExpiryDate,

        @Pattern(regexp = "^[A-Za-z0-9]+@[A-Za-z]+\\.[A-Za-z]+$", message = "Provide valid email address")
        String email,

        @NotBlank(message = "Password cannot be empty")
        @Pattern(regexp = ".*\\d.*", message = "Must contain at least one number")
        @Pattern(regexp = ".*\\W.*", message = "Must contain at least one special symbol")
        @Size(min = 6, max = 15, message = "Password must be within 6 to 15 chars")
        String password
) {
}
