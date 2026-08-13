package pl.piomin.services.employee.mapper;

import org.springframework.stereotype.Component;
import pl.piomin.services.employee.dto.OnboardEmployeeRequestDTO;
import pl.piomin.services.employee.entity.Employee;
import pl.piomin.services.employee.enums.OnboardingStatus;
import pl.piomin.services.employee.event.EmployeeOnboardEvent;

@Component
public class EmployeeMapper {

    public Employee toEntity(OnboardEmployeeRequestDTO requestDTO) {
        Employee employee = new Employee(
                requestDTO.getOrganizationId(), requestDTO.getDepartmentId(), requestDTO.getName(), requestDTO.getAge(), requestDTO.getPosition(), OnboardingStatus.ACTIVE);
        return employee;
    }

    public EmployeeOnboardEvent toActiveEmployee(Employee employee) {
        EmployeeOnboardEvent employeeOnboardEvent = new EmployeeOnboardEvent(
                employee.getId(), employee.getName(), employee.getDepartmentId(), employee.getOnboardingStatus()
        );
        return employeeOnboardEvent;
    }
}
