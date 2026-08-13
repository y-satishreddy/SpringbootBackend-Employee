package pl.piomin.services.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piomin.services.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
