package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.EmployeeReqDto;
import com.springboot.milezero.dto.request.EmployeeUpdateDto;
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
    public void updateManager(@PathVariable long id){
        employeeService.updateManager(id);
    }

    @PostMapping("/remove-manager/{id}")
    public void removeManager(@PathVariable long id){
        employeeService.removeManager(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id){
        employeeService.delete(id);
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable long id, @RequestBody EmployeeUpdateDto dto){
        employeeService.update(id, dto);
    }

    @GetMapping("/get-dashboard/me")
    public EmployeeDashboardDto getMeDashboard(Principal principal){
        String email = principal.getName();
        return employeeService.getMeDashboard(email);
    }
}
