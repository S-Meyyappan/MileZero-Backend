package com.springboot.milezero.dto.response;

public record BranchRespDto(
        long id,
        String name,
        String city,
        String address,
        String phone
) { }
