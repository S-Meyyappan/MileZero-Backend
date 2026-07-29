package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.request.FeatureReqDto;
import com.springboot.milezero.dto.response.FeatureRespDto;
import com.springboot.milezero.model.Feature;

public class FeatureMapper {

    public static Feature convertDtoToEntity(FeatureReqDto dto){
        Feature feature = new Feature();
        feature.setName(dto.name());
        feature.setFeatureGroup(dto.featureGroup());
        feature.setDescription(dto.description());
        return feature;
    }

    public static FeatureRespDto convertEntityToDto(Feature feature){
        return new FeatureRespDto(
                feature.getId(),
                feature.getName(),
                feature.getFeatureGroup(),
                feature.getDescription()
        );
    }
}
