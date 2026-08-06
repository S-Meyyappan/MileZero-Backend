package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.request.CustomerReqDto;
import com.springboot.milezero.dto.response.CustomerRespDto;
import com.springboot.milezero.model.Customer;

public class CustomerMapper {

    private CustomerMapper() {
        /* This utility class should not be instantiated */
    }

    public static Customer convertDtoToEntity(CustomerReqDto dto){
        Customer customer = new Customer();
        customer.setPhone(dto.phone());
        customer.setLicenseNo(dto.licenseNo());
        customer.setLicenseExpiryDate(dto.licenseExpiryDate());
        customer.setInsurancePolicyNo(dto.insurancePolicyNo());
        return customer;
    }

    public static CustomerRespDto convertEntityToDto(Customer customer){
        return new CustomerRespDto(
                customer.getId(),
                customer.getUser().getName(),
                customer.getLicenseNo(),
                customer.getLicenseExpiryDate(),
                customer.getPhone(),
                customer.getInsurancePolicyNo(),
                customer.getLicensePhoto()
        );
    }
}
