package pl.piomin.services.department.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class DepartmentAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long departmentId;

    private Long employeeId;

    private String action;

    private LocalDateTime createdAt;

    protected DepartmentAudit() {
    }

    public DepartmentAudit(
            Long departmentId,
            Long employeeId,
            String action,
            LocalDateTime createdAt) {

        this.departmentId = departmentId;
        this.employeeId = employeeId;
        this.action = action;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getAction() {
        return action;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}