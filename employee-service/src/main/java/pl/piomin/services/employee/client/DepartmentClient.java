package pl.piomin.services.employee.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.piomin.services.employee.dto.DepartmentResponseDTO;

@FeignClient(name="DEPARTMENT-SERVICE")
public interface DepartmentClient {

    @GetMapping("/{id}")
    public DepartmentResponseDTO getDepartment(@PathVariable Long id);
}