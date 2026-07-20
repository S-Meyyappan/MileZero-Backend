package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.request.EmployeeReqDto;
import com.springboot.milezero.dto.response.EmployeeRespDto;
import com.springboot.milezero.model.Employee;

public class EmployeeMapper {

    private EmployeeMapper() {
        /* This utility class should not be instantiated */
    }

    public static Employee convertDtoToEntity(EmployeeReqDto dto){
        Employee employee = new Employee();
        employee.setName(dto.name());
        employee.setPhone(dto.phone());
        return employee;
    }

    public static EmployeeRespDto convertEntityToDto(Employee employee){
        return new EmployeeRespDto(
                employee.getId(),
                employee.getName(),
                employee.getPhone(),
                employee.getUser().getRole(),
                employee.getBranch().getId(),
                employee.getBranch().getName(),
                employee.getBranch().getAddress()
        );
    }
}
