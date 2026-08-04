package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.AdminDto;
import com.springboot.milezero.enums.Role;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.model.User;
import com.springboot.milezero.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void add(AdminDto dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(Role.ADMIN);
        user.setActive(true);
        userRepository.save(user);
    }

    public User findByEmail(String name) {
        return userRepository.findByEmail(name)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Credentials"));
    }
}
