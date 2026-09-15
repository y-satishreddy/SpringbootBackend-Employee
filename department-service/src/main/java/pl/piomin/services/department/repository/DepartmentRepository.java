package pl.piomin.services.department.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piomin.services.department.entity.Department;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findByOrganizationId(Long organizationId);

}