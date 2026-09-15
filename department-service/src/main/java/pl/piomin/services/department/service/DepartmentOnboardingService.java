package pl.piomin.services.department.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.piomin.services.department.entity.Department;
import pl.piomin.services.department.entity.DepartmentAudit;
import pl.piomin.services.department.entity.Employee;
import pl.piomin.services.department.event.EmployeeOnboardEvent;
import pl.piomin.services.department.exception.DepartmentNotFoundException;
import pl.piomin.services.department.repository.DepartmentAuditRepository;
import pl.piomin.services.department.repository.DepartmentRepository;
import pl.piomin.services.department.repository.EmployeeRepository;

import java.time.LocalDateTime;

@Service
public class DepartmentOnboardingService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentAuditRepository departmentAuditRepository;

    public DepartmentOnboardingService(
            DepartmentRepository departmentRepository,
            EmployeeRepository employeeRepository,
            DepartmentAuditRepository departmentAuditRepository) {

        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.departmentAuditRepository = departmentAuditRepository;
    }

    @Transactional
    public void handleEmployeeOnboard(EmployeeOnboardEvent event) {

        // 1. Find the department
        Department department = departmentRepository
                .findById(event.getDepartmentId())
                .orElseThrow(() ->
                        new DepartmentNotFoundException(event.getDepartmentId()));

        // 2. Create Employee entity from Kafka event
        Employee employee = new Employee();

        employee.setId(event.getEmployeeId());
        employee.setName(event.getName());
        employee.setAge(event.getAge());
        employee.setPosition(event.getPosition());
        // Set the department relationship
        employee.setDepartment(department);

        // 3. Save employee
        employeeRepository.save(employee);

        // 4. Increment department headcount
        department.setHeadcount(department.getHeadcount() + 1);

        departmentRepository.save(department);

        // 5. Create audit record
        DepartmentAudit audit = new DepartmentAudit(
                department.getId(),
                employee.getId(),
                "EMPLOYEE_ONBOARDED",
                LocalDateTime.now()
        );

        departmentAuditRepository.save(audit);
    }
}