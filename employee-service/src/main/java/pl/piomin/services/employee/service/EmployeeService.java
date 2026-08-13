package pl.piomin.services.employee.service;

import pl.piomin.services.employee.dto.OnboardEmployeeRequestDTO;
import pl.piomin.services.employee.entity.Employee;

public interface EmployeeService {
    Employee onboardEmployee(OnboardEmployeeRequestDTO requestDTO);
}