package pl.piomin.services.employee.service;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pl.piomin.services.employee.client.DepartmentClient;
import pl.piomin.services.employee.dto.DepartmentResponseDTO;
import pl.piomin.services.employee.dto.OnboardEmployeeRequestDTO;
import pl.piomin.services.employee.enums.OnboardingStatus;
import pl.piomin.services.employee.event.EmployeeOnboardEvent;
import pl.piomin.services.employee.exception.DepartmentNotFoundException;
import pl.piomin.services.employee.exception.DepartmentServiceNotAvailableException;
import pl.piomin.services.employee.model.Employee;
import pl.piomin.services.employee.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final KafkaTemplate<String, EmployeeOnboardEvent> kafkaTemplate;
    private DepartmentClient departmentClient;
    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(DepartmentClient departmentClient, EmployeeRepository employeeRepository, KafkaTemplate<String, EmployeeOnboardEvent> kafkaTemplate) {
        this.departmentClient = departmentClient;
        this.employeeRepository = employeeRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Employee onboardEmployee(OnboardEmployeeRequestDTO requestDTO) {
        try {
            DepartmentResponseDTO departmentResponse = departmentClient.getDepartment(requestDTO.getDepartmentId());
            logger.info("Department Resppnse: {}", departmentResponse);
            Employee employee = new Employee(
                    requestDTO.getOrganizationId(), requestDTO.getDepartmentId(), requestDTO.getName(), requestDTO.getAge(), requestDTO.getPosition(), OnboardingStatus.ACTIVE);
            Employee response = employeeRepository.add(employee);

            EmployeeOnboardEvent employeeOnboardEvent = new EmployeeOnboardEvent(
                    response.getId(), response.getName(), response.getDepartmentId(), response.getOnboardingStatus()
            );
            kafkaTemplate.send("employee-onboard", employeeOnboardEvent);
            System.out.println("Sended to the kafka");
            return response;
        } catch (FeignException.NotFound exception) {
            throw new DepartmentNotFoundException(requestDTO.getDepartmentId());
        } catch (FeignException.ServiceUnavailable exception) {
            throw new DepartmentServiceNotAvailableException("Department service not availablet at the moment");
        }

    }
}