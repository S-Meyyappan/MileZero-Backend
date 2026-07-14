package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.BranchReqDto;
import com.springboot.milezero.mapper.request.BranchReqMapper;
import com.springboot.milezero.model.Branch;
import com.springboot.milezero.repository.BranchRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    public void add(BranchReqDto branchReqDto) {
        Branch branch = BranchReqMapper.convertDtoToEntity(branchReqDto);
        branchRepository.save(branch);
    }
}
