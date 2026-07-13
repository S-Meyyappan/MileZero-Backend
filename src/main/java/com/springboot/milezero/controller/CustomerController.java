package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.CustomerReqDto;
import com.springboot.milezero.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    /*
    Body:{
        name: "",
        phone: "",
        licenseNo: "",
        licenseExpiryDate: "",
        User : {
            email : "",
            password: ""
        }
    }
    */
    @PostMapping("/add")
    public void add(@Valid @RequestBody CustomerReqDto customerReqDto){
        customerService.add(customerReqDto);
    }
}
