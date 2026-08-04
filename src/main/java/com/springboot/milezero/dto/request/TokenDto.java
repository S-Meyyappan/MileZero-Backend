package com.springboot.milezero.dto.request;

import com.springboot.milezero.enums.Role;

public record TokenDto(
        String token,
        String username,
        Role role,
        String expirationTime
) {
}
