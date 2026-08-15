package com.springboot.milezero.service;

import com.springboot.milezero.dto.request.CustomerReqDto;
import com.springboot.milezero.dto.response.BookingStatsDto;
import com.springboot.milezero.dto.response.CustomerDashboardDto;
import com.springboot.milezero.dto.response.CustomerRespDto;
import com.springboot.milezero.enums.Role;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.mapper.CustomerDashboardMapper;
import com.springboot.milezero.mapper.CustomerMapper;
import com.springboot.milezero.mapper.UserMapper;
import com.springboot.milezero.model.Booking;
import com.springboot.milezero.model.Customer;
import com.springboot.milezero.model.User;
import com.springboot.milezero.repository.BookingRepository;
import com.springboot.milezero.repository.CustomerRepository;
import com.springboot.milezero.repository.UserRepository;
import com.springboot.milezero.utility.UploadUtility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    private final PasswordEncoder passwordEncoder;

    private static final String UPLOAD_DIR = "D:/MileZero/MileZero-Frontend/public/customer";

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

    public void updateCustomer(String email, @Valid String name, @Valid String phone, @Valid String licenseNo, @Valid LocalDate licenseExpiryDate, @Valid String insurancePolicyNo, MultipartFile imageFile) {
        Customer customer = customerRepository.fetchByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Invalid"));

        customer.getUser().setName(name);
        customer.setPhone(phone);
        customer.setLicenseNo(licenseNo);
        customer.setLicenseExpiryDate(licenseExpiryDate);
        customer.setInsurancePolicyNo(insurancePolicyNo);

        if(imageFile!= null && !imageFile.isEmpty()){
            // Validate image
            UploadUtility.validateImage(imageFile);

            // Upload image
            String imageUrl = UploadUtility.saveImage(imageFile, UPLOAD_DIR);

            customer.setLicensePhoto(imageUrl);
        }

        customerRepository.save(customer);
    }

    public CustomerDashboardDto getMeDashboard(String email) {
        Customer customer = customerRepository.fetchByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Email Invalid"));

        BookingStatsDto bookingStatsDto = bookingRepository.fetchCustomerBookingStats(customer.getId());

        Instant today = Instant.now();

        Pageable upcoming = PageRequest.of(0, 3);

        List<Booking> upcomingBookings = bookingRepository.fetchUpcomingCustomerBooking(customer.getId(), today, upcoming);

        Pageable recent = PageRequest.of(0, 3);

        List<Booking> recentBookings = bookingRepository.fetchRecentCustomerBookings(customer.getId(), today, recent );

        return CustomerDashboardMapper.convertToDto(customer, bookingStatsDto, upcomingBookings, recentBookings);
    }

    public void delete(long id) {
        Customer customer = customerRepository.fetchById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Id Invalid"));

        customerRepository.delete(customer);
        userRepository.delete(customer.getUser());
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
