package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.FeatureReqDto;
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

    public Feature add(@Valid FeatureReqDto featureReqDto) {
        Feature feature = FeatureMapper.convertDtoToEntity(featureReqDto);

        feature.setIsActive(true);

        feature = featureRepository.save(feature);

        return feature;
    }

    public List<Feature> getAll() {
        return featureRepository.findAll();
    }

    public List<Feature> getAllByFeatureGroup(FeatureGroup featureGroup) {
        return featureRepository.findByFeatureGroup(featureGroup);
    }
}
