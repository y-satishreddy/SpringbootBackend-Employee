package pl.piomin.services.department.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piomin.services.department.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}