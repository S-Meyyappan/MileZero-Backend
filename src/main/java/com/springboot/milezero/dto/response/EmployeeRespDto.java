package com.springboot.milezero.dto.response;

import com.springboot.milezero.enums.Role;

public record EmployeeRespDto(
        Long id,
        String name,
        String phone,
        Role role,
        Long branchId,
        String branchName,
        String city
) {
}
