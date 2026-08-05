package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.CustomerReqDto;
import com.springboot.milezero.dto.response.CustomerRespDto;
import com.springboot.milezero.enums.Role;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.mapper.CustomerMapper;
import com.springboot.milezero.mapper.UserMapper;
import com.springboot.milezero.model.Customer;
import com.springboot.milezero.model.User;
import com.springboot.milezero.repository.CustomerRepository;
import com.springboot.milezero.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerRespDto add(CustomerReqDto customerReqDto) {
        // 1. Get user from mapper
        User user = UserMapper.convertDtoToEntity(customerReqDto.name(),
                                                    customerReqDto.email(),
                                                    customerReqDto.password(),
                                                    Role.CUSTOMER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);

        // 2. Get Customer from mapper
        Customer customer = CustomerMapper.convertDtoToEntity(customerReqDto);

        // 3. Add user to customer
        customer.setUser(user);

        // 4. Save Customer
        customer = customerRepository.save(customer);
        return CustomerMapper.convertEntityToDto(customer);
    }

    public CustomerRespDto get(long id) {
        Customer customer = customerRepository.fetchById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Id Invalid"));

        return CustomerMapper.convertEntityToDto(customer);
    }

    public List<CustomerRespDto> getAll() {
        List<Customer> list = customerRepository.fetchAll();

        return list
                .stream()
                .map(CustomerMapper::convertEntityToDto)
                .toList();
    }

    public CustomerRespDto getCustomerByEmail(String email) {
        Customer customer = customerRepository.fetchByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Email Invalid"));

        return CustomerMapper.convertEntityToDto(customer);
    }

    /*
    public void update(@Valid CustomerReqDto dto, long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Id Invalid"));

        customer.setName(dto.name());
        customer.setPhone(dto.phone());
        customer.setLicenseNo(dto.licenseNo());
        customer.setLicenseExpiryDate(dto.licenseExpiryDate());
        customer.setInsurancePolicyNo(dto.insurancePolicyNo());

        customerRepository.save(customer);
    }
     */
}
