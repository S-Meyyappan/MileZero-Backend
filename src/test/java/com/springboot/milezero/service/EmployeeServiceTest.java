package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.EmployeeReqDto;
import com.springboot.milezero.dto.request.EmployeeUpdateDto;
import com.springboot.milezero.dto.response.EmployeeRespDto;
import com.springboot.milezero.enums.Role;
import com.springboot.milezero.exception.ResourceAlreadyExistsException;
import com.springboot.milezero.model.Branch;
import com.springboot.milezero.model.Employee;
import com.springboot.milezero.model.User;
import com.springboot.milezero.repository.BookingRepository;
import com.springboot.milezero.repository.BranchRepository;
import com.springboot.milezero.repository.EmployeeRepository;
import com.springboot.milezero.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Employee employee;
    private Branch branch;
    private User user;

    @BeforeEach
    void init() {

        Instant now = Instant.parse("2026-08-08T10:00:00Z");

        branch = new Branch();
        branch.setId(1L);

        user = new User();
        user.setId(10L);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setRole(Role.EMPLOYEE);
        user.setActive(true);

        employee = new Employee();
        employee.setId(100L);
        employee.setUser(user);
        employee.setBranch(branch);
    }


    @Test
    @DisplayName("Update Manager Successfully")
    void shouldUpdateEmployeeToManager() {

        // Given
        when(employeeRepository.findById(100L)).thenReturn(Optional.of(employee));
        when(employeeRepository.hasManager(1L)).thenReturn(Optional.empty());

        // When
        employeeService.updateManager(100L);

        // Then
        Assertions.assertEquals(Role.MANAGER,employee.getUser().getRole());

        verify(employeeRepository).save(employee);
    }


    @Test
    @DisplayName("Update Manager - Manager Already Exists")
    void shouldThrowWhenManagerAlreadyExists() {

        // Given
        when(employeeRepository.findById(100L)).thenReturn(Optional.of(employee));

        Employee existingManager = new Employee();

        when(employeeRepository.hasManager(1L)).thenReturn(Optional.of(existingManager));

        // When & Then
        Assertions.assertThrows(
                ResourceAlreadyExistsException.class,
                () -> employeeService.updateManager(100L)
        );

        // Employee must remain an employee
        Assertions.assertEquals(Role.EMPLOYEE,employee.getUser().getRole());

        // Nothing should be saved
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Delete Employee")
    void shouldDeleteEmployee() {

        // Given
        when(employeeRepository.fetchById(100L)).thenReturn(Optional.of(employee));

        // When
        employeeService.delete(100L);

        // Then
        verify(employeeRepository).delete(employee);
        verify(userRepository).delete(user);
    }

    @Test
    @DisplayName("Add Employee")
    void shouldAddEmployee() {

        // Given
        EmployeeReqDto request = new EmployeeReqDto(
                "John Doe",
                "9876543210",
                "john@example.com",
                "password1!",
                Role.EMPLOYEE,
                1L
        );

        User savedUser = new User(
                10L,
                "john@example.com",
                "John Doe",
                "encoded",
                Role.EMPLOYEE,
                true,
                Instant.parse("2026-08-08T10:00:00Z"),
                Instant.parse("2026-08-08T10:00:00Z")
        );

        Employee savedEmployee = new Employee();
        savedEmployee.setId(100L);
        savedEmployee.setUser(savedUser);
        savedEmployee.setBranch(branch);
        savedEmployee.setPhone("9876543210");

        when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));
        when(passwordEncoder.encode("password1!")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);

        // When
        EmployeeRespDto result = employeeService.add(request);

        // Then
        Assertions.assertNotNull(result);

        verify(userRepository).save(any(User.class));
        verify(employeeRepository).save(any(Employee.class));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userCaptor.capture());

        User actualUser = userCaptor.getValue();

        Assertions.assertEquals("encoded",actualUser.getPassword());
        Assertions.assertEquals("John Doe",actualUser.getName());
        Assertions.assertEquals("john@example.com",actualUser.getEmail());
        Assertions.assertEquals(Role.EMPLOYEE,actualUser.getRole());
    }

    @Test
    @DisplayName("Update Employee")
    void shouldUpdateEmployee() {

        // Given
        Branch newBranch = new Branch();
        newBranch.setId(2L);

        EmployeeUpdateDto request =
                new EmployeeUpdateDto(
                        "Updated John",
                        "9999999999",
                        2L
                );

        when(employeeRepository.fetchById(100L)).thenReturn(Optional.of(employee));
        when(branchRepository.findById(2L)).thenReturn(Optional.of(newBranch));

        // When
        employeeService.update(100L, request);

        // Then
        Assertions.assertEquals("Updated John",employee.getUser().getName());
        Assertions.assertEquals("9999999999",employee.getPhone());
        Assertions.assertEquals(newBranch,employee.getBranch());
        verify(employeeRepository).save(employee);
    }
}