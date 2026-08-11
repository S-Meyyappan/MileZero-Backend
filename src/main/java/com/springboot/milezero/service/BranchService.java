package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.BranchReqDto;
import com.springboot.milezero.dto.response.BranchRespDto;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.mapper.BranchMapper;
import com.springboot.milezero.model.Branch;
import com.springboot.milezero.repository.BranchRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;

    public BranchRespDto add(BranchReqDto branchReqDto) {
        Branch branch = BranchMapper.convertDtoToEntity(branchReqDto);
        branch = branchRepository.save(branch);
        return BranchMapper.convertEntityToDto(branch);
    }

    public List<BranchRespDto> getAll() {
        List<Branch> list = branchRepository.findAll();
        return list.stream()
                .map(BranchMapper::convertEntityToDto)
                .toList();
    }

    public void update(long id, @Valid BranchReqDto dto) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch Id Invalid"));

        branch.setName(dto.name());
        branch.setCity(dto.city());
        branch.setAddress(dto.address());
        branch.setPhone(dto.phone());
        branchRepository.save(branch);
    }
}
