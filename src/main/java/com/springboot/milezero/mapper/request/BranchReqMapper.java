package com.springboot.milezero.mapper.request;

import com.springboot.milezero.dto.request.BranchReqDto;
import com.springboot.milezero.model.Branch;

public class BranchReqMapper {

    public static Branch convertDtoToEntity(BranchReqDto dto){
        Branch branch = new Branch();
        branch.setName(dto.name());
        branch.setAddress(dto.address());
        branch.setCity(dto.city());
        branch.setPhone(dto.phone());
        return branch;
    }
}
