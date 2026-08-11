package com.springboot.milezero.service;

import com.springboot.milezero.enums.Role;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.model.User;
import com.springboot.milezero.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private MyUserSecurityService myUserSecurityService;

    private User user;

    @BeforeEach
    void init() {

        Instant now = Instant.parse("2026-08-08T10:00:00.000Z");

        user = new User(
                1L,
                "john.doe@example.com",
                "JohnDoe",
                "encoded-password",
                Role.CUSTOMER,
                true,
                now,
                now
        );
    }

    @Test
    @DisplayName("Find User By Email")
    void findByEmailTest() {

        // Given
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));

        // When
        User result = userService.findByEmail("john.doe@example.com");

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("john.doe@example.com",result.getEmail());
        Assertions.assertEquals("JohnDoe",result.getName());

        verify(userRepository).findByEmail("john.doe@example.com");
    }

    @Test
    @DisplayName("Load User By Username")
    void loadUserByUsernameTest() {

        // Given
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));

        // When
        UserDetails result =myUserSecurityService.loadUserByUsername("john.doe@example.com");

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("john.doe@example.com",result.getUsername());
        Assertions.assertEquals("encoded-password",result.getPassword());
        Assertions.assertTrue(result.isEnabled());

        verify(userRepository).findByEmail("john.doe@example.com");
    }

    @Test
    @DisplayName("Find User By Email - Not Found")
    void findByEmailNotFoundTest() {

        // Given
        when(userRepository.findByEmail("invalid@example.com")).thenReturn(Optional.empty());

        // When & Then
        Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> userService.findByEmail("invalid@example.com")
        );

        verify(userRepository).findByEmail("invalid@example.com");
    }

    @Test
    @DisplayName("Load User By Username - Not Found")
    void loadUserByUsernameNotFoundTest() {

        // Given
        when(userRepository.findByEmail("invalid@example.com")).thenReturn(Optional.empty());

        // When & Then
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> myUserSecurityService.loadUserByUsername("invalid@example.com")
        );

        verify(userRepository).findByEmail("invalid@example.com");
    }
}