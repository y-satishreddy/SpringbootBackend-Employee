package pl.piomin.services.employee.event;


import pl.piomin.services.employee.enums.OnboardingStatus;

public class EmployeeOnboardEvent {

    private Long employeeId;
    private String name;
    private Long departmentId;
    private OnboardingStatus onboardingStatus;

    public EmployeeOnboardEvent() {
    }

    public EmployeeOnboardEvent(
            Long employeeId,
            String name,
            Long departmentId,
            OnboardingStatus onboardingStatus) {

        this.employeeId = employeeId;
        this.name = name;
        this.departmentId = departmentId;
        this.onboardingStatus = onboardingStatus;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public OnboardingStatus getOnboardingStatus() {
        return onboardingStatus;
    }

    public void setOnboardingStatus(OnboardingStatus onboardingStatus) {
        this.onboardingStatus = onboardingStatus;
    }
}
