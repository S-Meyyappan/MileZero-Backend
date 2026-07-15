package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.EmployeeReqDto;

import com.springboot.milezero.mapper.EmployeeMapper;
import com.springboot.milezero.mapper.UserMapper;
import com.springboot.milezero.model.Employee;
import com.springboot.milezero.model.User;
import com.springboot.milezero.repository.EmployeeRepository;
import com.springboot.milezero.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public void add(EmployeeReqDto employeeReqDto) {
        // 1. Add user to db
        User user = UserMapper.convertDtoToEntity(employeeReqDto.email(),
                                                    employeeReqDto.password(),
                                                    employeeReqDto.role());
        user = userRepository.save(user);

        // 2. Get Employee from Dto
        Employee employee = EmployeeMapper.convertDtoToEntity(employeeReqDto);
        employee.setUser(user);

        // 3. Add employee
        employeeRepository.save(employee);
    }
}
