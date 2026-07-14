package com.springboot.milezero.mapper.request;

import com.springboot.milezero.dto.request.AddonReqDto;
import com.springboot.milezero.model.Addon;

public class AddonReqMapper {

    public static Addon convertDtoToEntity(AddonReqDto dto){
        Addon addon = new Addon();
        addon.setName(dto.name());
        addon.setPricePerDay(dto.pricePerDay());
        return addon;
    }
}
