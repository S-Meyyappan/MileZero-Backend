package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.EmployeeReqDto;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.mapper.EmployeeMapper;
import com.springboot.milezero.mapper.UserMapper;
import com.springboot.milezero.model.Branch;
import com.springboot.milezero.model.Employee;
import com.springboot.milezero.model.User;
import com.springboot.milezero.repository.BranchRepository;
import com.springboot.milezero.repository.EmployeeRepository;
import com.springboot.milezero.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;

    @Transactional
    public void add(EmployeeReqDto employeeReqDto) {
        // 1. Validate branch id
        Branch branch = branchRepository.findById(employeeReqDto.branchId())
            .orElseThrow(() -> new ResourceNotFoundException("Branch Id Invalid"));

        // 2. Add user to db
        User user = UserMapper.convertDtoToEntity(employeeReqDto.email(),
                                                    employeeReqDto.password(),
                                                    employeeReqDto.role());
        user = userRepository.save(user);

        // 3. Get Employee from Dto
        Employee employee = EmployeeMapper.convertDtoToEntity(employeeReqDto);
        employee.setUser(user);
        employee.setBranch(branch);

        // 4. Add employee
        employeeRepository.save(employee);
    }
}
