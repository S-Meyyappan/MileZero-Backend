package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.EmployeeReqDto;
import com.springboot.milezero.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    /*
    Body:{
        name: "",
        phone: "",
        designation: "",
        User:{
            email: "",
            password: "",
            role: ""
        }
    }
    */
    @PostMapping("/add")
    public void add(@Valid @RequestBody EmployeeReqDto employeeReqDto){
        employeeService.add(employeeReqDto);
    }
}
