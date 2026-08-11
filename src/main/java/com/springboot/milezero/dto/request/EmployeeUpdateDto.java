package com.springboot.milezero.dto.request;

public record EmployeeUpdateDto(
        String name,
        String phone,
        Long branchId
) {
}
