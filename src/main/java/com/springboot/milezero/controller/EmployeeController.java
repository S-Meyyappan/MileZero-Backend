package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.EmployeeReqDto;
import com.springboot.milezero.dto.response.EmployeeDashboardDto;
import com.springboot.milezero.dto.response.EmployeeRespDto;
import com.springboot.milezero.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employee")
@CrossOrigin("http://localhost:5173")
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
    public EmployeeRespDto add(@Valid @RequestBody EmployeeReqDto employeeReqDto){
        return employeeService.add(employeeReqDto);
    }

    @GetMapping("/get/{id}")
    public EmployeeRespDto get(@PathVariable long id){
        return employeeService.get(id);
    }

    @GetMapping("/get-all")
    public List<EmployeeRespDto> getALl(){
        return employeeService.getAll();
    }

    @PostMapping("/set-manager/{id}")
    public EmployeeRespDto updateManager(@PathVariable long id){
        return employeeService.updateManager(id);
    }

    @GetMapping("/get-dashboard/me")
    public EmployeeDashboardDto getMeDashboard(Principal principal){
        String email = principal.getName();
        return employeeService.getMeDashboard(email);
    }
}
