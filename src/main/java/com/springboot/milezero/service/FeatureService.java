package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.FeatureReqDto;
import com.springboot.milezero.dto.response.FeatureRespDto;
import com.springboot.milezero.enums.FeatureGroup;
import com.springboot.milezero.mapper.FeatureMapper;
import com.springboot.milezero.model.Feature;
import com.springboot.milezero.repository.FeatureRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeatureService {

    private final FeatureRepository featureRepository;

    public FeatureRespDto add(@Valid FeatureReqDto featureReqDto) {
        Feature feature = FeatureMapper.convertDtoToEntity(featureReqDto);

        feature.setIsActive(true);

        feature = featureRepository.save(feature);

        return FeatureMapper.convertEntityToDto(feature);
    }

    public List<FeatureRespDto> getAll() {
        List<Feature> list = featureRepository.findAll();

        return list
                .stream()
                .map(FeatureMapper::convertEntityToDto)
                .toList();
    }

    public List<FeatureRespDto> getAllByFeatureGroup(FeatureGroup featureGroup) {
        List<Feature> list = featureRepository.findByFeatureGroup(featureGroup);

        return list
                .stream()
                .map(FeatureMapper::convertEntityToDto)
                .toList();
    }
}
