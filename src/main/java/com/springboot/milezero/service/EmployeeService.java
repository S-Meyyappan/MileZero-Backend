package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.EmployeeReqDto;
import com.springboot.milezero.dto.request.EmployeeUpdateDto;
import com.springboot.milezero.dto.response.BookingStatsDto;
import com.springboot.milezero.dto.response.EmployeeDashboardDto;
import com.springboot.milezero.dto.response.EmployeeRespDto;
import com.springboot.milezero.enums.Role;
import com.springboot.milezero.exception.ResourceAlreadyExistsException;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.mapper.EmployeeDashboardMapper;
import com.springboot.milezero.mapper.EmployeeMapper;
import com.springboot.milezero.mapper.UserMapper;
import com.springboot.milezero.model.Booking;
import com.springboot.milezero.model.Branch;
import com.springboot.milezero.model.Employee;
import com.springboot.milezero.model.User;
import com.springboot.milezero.repository.BookingRepository;
import com.springboot.milezero.repository.BranchRepository;
import com.springboot.milezero.repository.EmployeeRepository;
import com.springboot.milezero.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final BookingRepository bookingRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public EmployeeRespDto add(EmployeeReqDto employeeReqDto) {
        // 1. Validate branch id
        Branch branch = branchRepository.findById(employeeReqDto.branchId())
            .orElseThrow(() -> new ResourceNotFoundException("Branch Id Invalid"));

        // 2. Add user to db
        User user = UserMapper.convertDtoToEntity(employeeReqDto.name(),
                                                    employeeReqDto.email(),
                                                    employeeReqDto.password(),
                                                    employeeReqDto.role());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(user.getRole() == Role.MANAGER){
            long branchId = employeeReqDto.branchId();

            // Check if there is a manager for the same branch
            if(employeeRepository.hasManager(branchId).isPresent()){
                throw new ResourceAlreadyExistsException("Manager Already Exists");
            }
        }

        user = userRepository.save(user);

        // 3. Get Employee from Dto
        Employee employee = EmployeeMapper.convertDtoToEntity(employeeReqDto);
        employee.setUser(user);
        employee.setBranch(branch);

        // 4. Add employee
        employee = employeeRepository.save(employee);
        return EmployeeMapper.convertEntityToDto(employee);
    }

    public EmployeeRespDto get(long id) {
        Employee employee = employeeRepository.fetchById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Id Invalid"));
        return EmployeeMapper.convertEntityToDto(employee);
    }

    public List<EmployeeRespDto> getAll() {
        List<Employee> list = employeeRepository.fetchAll();
        return list
                .stream()
                .map(EmployeeMapper::convertEntityToDto)
                .toList();
    }

    public void updateManager(long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Id Invalid"));

        long branchId = employee.getBranch().getId();

        // Check if there is a manager for the same branch
        if(employeeRepository.hasManager(branchId).isPresent()){
                throw new ResourceAlreadyExistsException("Manager Already Exists");
        }

        employee.getUser().setRole(Role.MANAGER);

        employeeRepository.save(employee);
    }

    public void removeManager(long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Id Invalid"));

        // Check if there is a manager for the same branch
        if(employee.getUser().getRole() != Role.MANAGER){
            throw new ResourceAlreadyExistsException("Not a valid Manager");
        }

        employee.getUser().setRole(Role.EMPLOYEE);

        employeeRepository.save(employee);
    }

    public void delete(long id) {
        Employee employee = employeeRepository.fetchById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Id Invalid"));

        employeeRepository.delete(employee);
        userRepository.delete(employee.getUser());
    }

    public void update(long id, EmployeeUpdateDto dto) {
        Employee employee = employeeRepository.fetchById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Id Invalid"));

        Branch branch = branchRepository.findById(dto.branchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch Id Invalid"));

        employee.getUser().setName(dto.name());
        employee.setPhone(dto.phone());
        employee.setBranch(branch);

        employeeRepository.save(employee);
    }

    public EmployeeDashboardDto getMeDashboard(String email) {
        Employee employee = employeeRepository.fetchByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Email Invalid"));

        // Branch Id
        long id = employee.getBranch().getId();

        BookingStatsDto bookingStats = bookingRepository.fetchEmployeeBookingStats(id);

        Instant today = Instant.now();

        Instant tomorrow = today.plus(2, ChronoUnit.DAYS);

        Pageable pageable = PageRequest.of(0, 4 );

        List<Booking> upcomingPickups = bookingRepository.fetchEmployeeUpcomingPickups(id, today, tomorrow, pageable);

        List<Booking> upcomingReturns = bookingRepository.fetchEmployeeUpcomingReturns(id, today, tomorrow, pageable);

        List<Booking> recentBookings = bookingRepository.fetchEmployeeRecentBookings(id, today, pageable);

        return EmployeeDashboardMapper.convertToEntity(employee, bookingStats, upcomingPickups, upcomingReturns, recentBookings);
    }
}
