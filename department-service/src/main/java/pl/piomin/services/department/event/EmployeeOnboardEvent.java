package pl.piomin.services.department.event;

public class EmployeeOnboardEvent {

    private Long employeeId;
    private Long departmentId;
    private String name;
    private String position;
    private int age;
    public EmployeeOnboardEvent() {
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }
}