package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.request.AddonReqDto;
import com.springboot.milezero.dto.response.AddonRespDto;
import com.springboot.milezero.model.Addon;

public class AddonMapper {

    private AddonMapper() {
        /* This utility class should not be instantiated */
    }

    public static Addon convertDtoToEntity(AddonReqDto dto){
        Addon addon = new Addon();
        addon.setName(dto.name());
        addon.setPricePerDay(dto.pricePerDay());
        return addon;
    }

    public static AddonRespDto convertEntityTODto(Addon addon){
        return new AddonRespDto(
                addon.getName(),
                addon.getPricePerDay()
        );
    }
}
