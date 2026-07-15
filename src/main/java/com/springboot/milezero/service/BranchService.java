package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.BranchReqDto;
import com.springboot.milezero.dto.response.BranchRespDto;
import com.springboot.milezero.mapper.BranchMapper;
import com.springboot.milezero.model.Branch;
import com.springboot.milezero.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;

    public void add(BranchReqDto branchReqDto) {
        Branch branch = BranchMapper.convertDtoToEntity(branchReqDto);
        branchRepository.save(branch);
    }

    public List<BranchRespDto> getAll() {
        List<Branch> list = branchRepository.findAll();
        return list.stream()
                .map(BranchMapper::convertEntityToDto)
                .toList();
    }
}
