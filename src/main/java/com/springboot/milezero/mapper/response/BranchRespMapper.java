package com.springboot.milezero.mapper.response;

import com.springboot.milezero.dto.response.BranchRespDto;
import com.springboot.milezero.model.Branch;

public class BranchRespMapper {

    public static BranchRespDto convertEntityToDto(Branch branch){
        return new BranchRespDto(
                branch.getId(),
                branch.getName(),
                branch.getCity(),
                branch.getAddress(),
                branch.getPhone()
        );
    }
}
