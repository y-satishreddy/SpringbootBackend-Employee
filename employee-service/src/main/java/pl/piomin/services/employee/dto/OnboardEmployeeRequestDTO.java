package pl.piomin.services.employee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OnboardEmployeeRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Age is required")
    @Positive(message = "Age must be greater than 0")
    private Integer age;

    @NotBlank(message = "Position is required")
    private String position;

    @NotNull(message = "Organization ID is required")
    @Positive(message = "Organization ID must be greater than 0")
    private Long organizationId;

    @NotNull(message = "Department ID is required")
    @Positive(message = "Department ID must be greater than 0")
    private Long departmentId;

    public OnboardEmployeeRequestDTO() {
    }

    public OnboardEmployeeRequestDTO(
            String name,
            Integer age,
            String position,
            Long organizationId,
            Long departmentId) {

        this.name = name;
        this.age = age;
        this.position = position;
        this.organizationId = organizationId;
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}