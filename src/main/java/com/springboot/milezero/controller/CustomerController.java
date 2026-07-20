package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.CustomerReqDto;
import com.springboot.milezero.dto.response.CustomerRespDto;
import com.springboot.milezero.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public CustomerRespDto add(@Valid @RequestBody CustomerReqDto customerReqDto){
        return customerService.add(customerReqDto);
    }

    @GetMapping("/get/{id}")
    public CustomerRespDto get(@PathVariable long id){
        return customerService.get(id);
    }

    @GetMapping("/get-all")
    public List<CustomerRespDto> getALl(){
        return customerService.getAll();
    }
    /*
    @PutMapping("/update/{id}")
    public void update( @PathVariable long id,
                        @Valid @RequestBody CustomerReqDto customerreqDto){
        customerService.update(customerreqDto, id);
    }
     */
}
