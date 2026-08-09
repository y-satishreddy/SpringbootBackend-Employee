package pl.piomin.services.employee.dto;

public class DepartmentResponseDTO {
    private Long id;
    private Long organizationId;

    public DepartmentResponseDTO() {
    }

    public DepartmentResponseDTO(Long id, Long organizationId) {
        this.id = id;
        this.organizationId = organizationId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String toString() {
        return "DepartmentResponseDTO [id= " + id + ", organizationId= " + organizationId + "]";
    }
}