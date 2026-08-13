package pl.piomin.services.employee.entity;

import jakarta.persistence.*;
import pl.piomin.services.employee.enums.OnboardingStatus;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long organizationId;

    private Long departmentId;

    private String name;

    private Integer age;

    private String position;

    @Enumerated(EnumType.STRING)
    private OnboardingStatus onboardingStatus;

    protected Employee() {
    }

    public Employee(
            Long organizationId,
            Long departmentId,
            String name,
            Integer age,
            String position,
            OnboardingStatus onboardingStatus) {

        this.organizationId = organizationId;
        this.departmentId = departmentId;
        this.name = name;
        this.age = age;
        this.position = position;
        this.onboardingStatus = onboardingStatus;
    }

    public Long getId() {
        return id;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getPosition() {
        return position;
    }

    public OnboardingStatus getOnboardingStatus() {
        return onboardingStatus;
    }

    public void setOnboardingStatus(OnboardingStatus onboardingStatus) {
        this.onboardingStatus = onboardingStatus;
    }
}