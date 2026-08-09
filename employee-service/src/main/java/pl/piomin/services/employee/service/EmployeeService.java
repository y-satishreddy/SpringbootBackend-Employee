package pl.piomin.services.employee.service;

import pl.piomin.services.employee.dto.OnboardEmployeeRequestDTO;
import pl.piomin.services.employee.dto.OnboardEmployeeResponseDTO;
import pl.piomin.services.employee.model.Employee;

public interface EmployeeService {
    Employee onboardEmployee(OnboardEmployeeRequestDTO requestDTO);
}
