package pl.piomin.services.department.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piomin.services.department.entity.DepartmentAudit;

public interface DepartmentAuditRepository
        extends JpaRepository<DepartmentAudit, Long> {
}