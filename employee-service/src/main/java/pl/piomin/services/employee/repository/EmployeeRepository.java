package pl.piomin.services.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piomin.services.employee.entity.Employee;
import pl.piomin.services.employee.enums.OnboardingStatus;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    List<Employee> findByOnboardingStatus(OnboardingStatus status);
}
