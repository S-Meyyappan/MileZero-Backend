package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.CustomerReqDto;
import com.springboot.milezero.enums.Role;
import com.springboot.milezero.mapper.CustomerMapper;
import com.springboot.milezero.mapper.UserMapper;
import com.springboot.milezero.model.Customer;
import com.springboot.milezero.model.User;
import com.springboot.milezero.repository.CustomerRepository;
import com.springboot.milezero.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public void add(CustomerReqDto customerReqDto) {
        // 1. Get user from mapper
        User user = UserMapper.convertDtoToEntity(customerReqDto.email(),
                                                    customerReqDto.password(),
                                                    Role.CUSTOMER);
        user = userRepository.save(user);

        // 2. Get Customer from mapper
        Customer customer = CustomerMapper.convertDtoToEntity(customerReqDto);

        // 3. Add user to customer
        customer.setUser(user);

        // 4. Save Customer
        customerRepository.save(customer);
    }
}
