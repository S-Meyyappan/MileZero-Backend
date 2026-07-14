package com.springboot.milezero.mapper.request;

import com.springboot.milezero.dto.request.EmployeeReqDto;
import com.springboot.milezero.model.Employee;

public class EmployeeReqMapper {

    public static Employee convertDtoToEntity(EmployeeReqDto dto){
        Employee employee = new Employee();
        employee.setName(dto.name());
        employee.setPhone(dto.phone());
        employee.setDesignation(dto.designation());
        return employee;
    }
}
