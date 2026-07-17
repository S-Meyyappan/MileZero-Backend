package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.AddonReqDto;
import com.springboot.milezero.dto.response.AddonRespDto;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.mapper.AddonMapper;
import com.springboot.milezero.model.Addon;
import com.springboot.milezero.repository.AddonRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddonService {

    private final AddonRepository addonRepository;

    public void add(AddonReqDto addonReqDto) {
        Addon addon = AddonMapper.convertDtoToEntity(addonReqDto);
        addonRepository.save(addon);
    }

    public AddonRespDto get(Long id) {
        Addon addon = addonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Addon not found"));

        return AddonMapper.convertEntityTODto(addon);
    }

    public List<AddonRespDto> getAll() {
        List<Addon> list = addonRepository.findAll();

        return list
                .stream()
                .map(AddonMapper::convertEntityTODto)
                .toList();
    }

    public void update(@Valid AddonReqDto dto, long id) {
        Addon addon = addonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Addon not found"));

        addon.setName(dto.name());
        addon.setPricePerDay(dto.pricePerDay());

        addonRepository.save(addon);
    }
}
