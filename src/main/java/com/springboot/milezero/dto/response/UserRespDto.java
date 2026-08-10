package com.springboot.milezero.dto.response;

import com.springboot.milezero.enums.Role;

public record UserRespDto(
        String username,
        Role role
) {
}
