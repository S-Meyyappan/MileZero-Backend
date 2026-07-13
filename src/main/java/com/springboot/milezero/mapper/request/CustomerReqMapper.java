package com.springboot.milezero.mapper.request;

import com.springboot.milezero.dto.request.CustomerReqDto;
import com.springboot.milezero.model.Customer;

public class CustomerReqMapper {

    public static Customer convertDtoToEntity(CustomerReqDto dto){
        Customer customer = new Customer();
        customer.setName(dto.name());
        customer.setPhone(dto.phone());
        customer.setLicenseNo(dto.licenseNo());
        customer.setLicenseExpiryDate(dto.licenseExpiryDate());
        return customer;
    }
}
