package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.AddonReqDto;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.mapper.AddonMapper;
import com.springboot.milezero.model.Addon;
import com.springboot.milezero.repository.AddonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddonService {

    private final AddonRepository addonRepository;

    public void add(AddonReqDto addonReqDto) {
        Addon addon = AddonMapper.convertDtoToEntity(addonReqDto);
        addonRepository.save(addon);
    }

    public Addon get(Long id) {
        return addonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Addon not found"));
    }
}
