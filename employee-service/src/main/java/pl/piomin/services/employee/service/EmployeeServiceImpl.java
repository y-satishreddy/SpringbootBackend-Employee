package pl.piomin.services.employee.service;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piomin.services.employee.client.DepartmentClient;
import pl.piomin.services.employee.dto.DepartmentResponseDTO;
import pl.piomin.services.employee.dto.OnboardEmployeeRequestDTO;
import pl.piomin.services.employee.entity.Employee;
import pl.piomin.services.employee.event.EmployeeOnboardEvent;
import pl.piomin.services.employee.exception.DepartmentNotFoundException;
import pl.piomin.services.employee.exception.DepartmentServiceNotAvailableException;
import pl.piomin.services.employee.mapper.EmployeeMapper;
import pl.piomin.services.employee.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final DepartmentClient departmentClient;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final OutboxService outboxService;

    public EmployeeServiceImpl(OutboxService outboxService, EmployeeMapper employeeMapper, DepartmentClient departmentClient, EmployeeRepository employeeRepository) {
        this.departmentClient = departmentClient;
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.outboxService = outboxService;
    }

    @Transactional
    public Employee onboardEmployee(OnboardEmployeeRequestDTO requestDTO) {
        try {
            DepartmentResponseDTO departmentResponse = departmentClient.getDepartment(requestDTO.getDepartmentId());
            logger.info("Department Resppnse: {}", departmentResponse);
            Employee employee = employeeMapper.toEntity(requestDTO);
            Employee response = employeeRepository.save(employee);
            EmployeeOnboardEvent employeeOnboardEvent = employeeMapper.toActiveEmployee(response);
            outboxService.saveOutboxEvent(employeeOnboardEvent);
            return response;
        } catch (FeignException.NotFound exception) {
            throw new DepartmentNotFoundException(requestDTO.getDepartmentId());
        } catch (FeignException.ServiceUnavailable exception) {
            throw new DepartmentServiceNotAvailableException("Department service not availablet at the moment");
        }

    }
}