package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.request.BranchReqDto;
import com.springboot.milezero.dto.response.BranchRespDto;
import com.springboot.milezero.model.Branch;

public class BranchMapper {

    private BranchMapper() {
        /* This utility class should not be instantiated */
    }

    public static Branch convertDtoToEntity(BranchReqDto dto){
        Branch branch = new Branch();
        branch.setName(dto.name());
        branch.setAddress(dto.address());
        branch.setCity(dto.city());
        branch.setPhone(dto.phone());
        return branch;
    }

    public static BranchRespDto convertEntityToDto(Branch branch){
        return new BranchRespDto(
                branch.getName(),
                branch.getCity(),
                branch.getAddress(),
                branch.getPhone()
        );
    }
}
