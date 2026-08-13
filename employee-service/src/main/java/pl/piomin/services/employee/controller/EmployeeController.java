package pl.piomin.services.employee.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.piomin.services.employee.dto.OnboardEmployeeRequestDTO;
import pl.piomin.services.employee.entity.Employee;
import pl.piomin.services.employee.service.EmployeeService;

@RestController
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping("/employee")
    public Employee addEmp(@Valid @RequestBody OnboardEmployeeRequestDTO request){
        return employeeService.onboardEmployee(request);
    }
}
