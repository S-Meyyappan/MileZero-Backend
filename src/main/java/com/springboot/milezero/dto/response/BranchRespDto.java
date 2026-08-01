package com.springboot.milezero.dto.response;

public record BranchRespDto(
        Long id,
        String name,
        String city,
        String address,
        String phone
) { }
