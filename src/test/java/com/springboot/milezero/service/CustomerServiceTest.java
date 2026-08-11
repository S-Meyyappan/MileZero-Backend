package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.CustomerReqDto;
import com.springboot.milezero.dto.response.CustomerRespDto;
import com.springboot.milezero.enums.Role;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.model.Customer;
import com.springboot.milezero.model.User;
import com.springboot.milezero.repository.BookingRepository;
import com.springboot.milezero.repository.CustomerRepository;
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
import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private PasswordEncoder passwordEncoder;


    private Instant now;
    private LocalDate date;

    private Customer customer1;
    private User user1;

    @BeforeEach
    public void init(){

        now = Instant.parse("2026-08-08T10:00:00.000Z");
        date = LocalDate.parse("2026-08-08");

        user1 = new User(1L, "john.doe@example.com", "JohnDoe", "enocded pass", Role.CUSTOMER, true, now, now);
        customer1 = new Customer(1L, "9876543210", "DL-12345", date, "photo.jpg", "INS-123", user1, now, now);

    }


    @Test
    @DisplayName("Add Customer")
    public void addCustomerTest(){
        // Given
        CustomerReqDto customerInsertDto = new CustomerReqDto("JohnDoe", "9876543210", "DL-12345",date, "INS-123", "john.doe@example.com", "password");

        User encodedUser = new User(1L, "john.doe@example.com", "JohnDoe", "encoded", Role.CUSTOMER, true, now, now);
        Customer encodedCustomer = new Customer(1L, "9876543210", "DL-12345", date, "photo.jpg", "INS-123", encodedUser, now, now);
        CustomerRespDto expected = new CustomerRespDto(1L, "JohnDoe", "DL-12345", date, "9876543210", "INS-123", "photo.jpg");

        // When
        when(passwordEncoder.encode("password")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(encodedUser);
        when(customerRepository.save(any(Customer.class))).thenReturn(encodedCustomer);

        // Then
        CustomerRespDto result = customerService.add(customerInsertDto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User actualUser = userCaptor.getValue();
        Assertions.assertEquals(encodedUser.getPassword(), actualUser.getPassword());

        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Get Customer")
    void getCustomerTest() {

        // Given
        when(customerRepository.fetchById(1L))
                .thenReturn(java.util.Optional.of(customer1));

        // When
        CustomerRespDto result = customerService.get(1L);

        // Then
        Assertions.assertNotNull(result);

        Assertions.assertEquals(1L, result.id());
        Assertions.assertEquals("JohnDoe", result.name());
        Assertions.assertEquals("DL-12345", result.licenseNo());
        Assertions.assertEquals(date, result.licenseExpiryDate());
        Assertions.assertEquals("9876543210", result.phone());
        Assertions.assertEquals("INS-123", result.insurancePolicyNo());
        Assertions.assertEquals("photo.jpg", result.licensePhoto());
    }

    @Test
    @DisplayName("Get Customer - Not Found")
    void getCustomerNotFoundTest() {

        // Given
        when(customerRepository.fetchById(2L))
                .thenReturn(java.util.Optional.empty());

        // When & Then
        Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> customerService.get(2L)
        );

        verify(customerRepository).fetchById(2L);
    }

}
