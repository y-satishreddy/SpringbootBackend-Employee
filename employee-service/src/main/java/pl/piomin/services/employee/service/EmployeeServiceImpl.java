package pl.piomin.services.employee.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piomin.services.employee.dto.DepartmentResponseDTO;
import pl.piomin.services.employee.dto.OnboardEmployeeRequestDTO;
import pl.piomin.services.employee.entity.Employee;
import pl.piomin.services.employee.exception.DepartmentNotFoundException;
import pl.piomin.services.employee.exception.DepartmentServiceNotAvailableException;
import pl.piomin.services.employee.mapper.EmployeeMapper;
import pl.piomin.services.employee.repository.EmployeeRepository;

import java.util.List;

import static pl.piomin.services.employee.enums.OnboardingStatus.ACTIVE;
import static pl.piomin.services.employee.enums.OnboardingStatus.PENDING_VALIDATION;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final DepartmentValidationService departmentValidationService;
    private final EmployeeOnboardingCompletionService onboardingCompletionService;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(
            DepartmentValidationService departmentValidationService,
            EmployeeOnboardingCompletionService onboardingCompletionService,
            EmployeeRepository employeeRepository,
            EmployeeMapper employeeMapper) {

        this.departmentValidationService = departmentValidationService;
        this.onboardingCompletionService = onboardingCompletionService;
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Transactional
    @Override
    public Employee onboardEmployee(OnboardEmployeeRequestDTO requestDTO) {

        logger.info("Request entered service | name={} | departmentId={} ", requestDTO.getName(),requestDTO.getDepartmentId());
        
        try {

            // 1. Validate department
            DepartmentResponseDTO departmentResponse =
                    departmentValidationService.departmentResponseDTO(
                            requestDTO.getDepartmentId()
                    );

            logger.info(
                    "Department validation successful: {}",
                    departmentResponse
            );

            // 2. Department exists → create ACTIVE employee
            Employee employee =
                    employeeMapper.toEntity(requestDTO);

            employee.setOnboardingStatus(ACTIVE);

            Employee response =
                    employeeRepository.save(employee);

            // 3. Continue onboarding
            return onboardingCompletionService.completeOnboarding(response);

        } catch (DepartmentServiceNotAvailableException exception) {

            // Department service unavailable.
            // Save employee as PENDING and STOP.

            logger.warn(
                    "Department service unavailable. " +
                            "Saving employee as PENDING_VALIDATION. departmentId={}",
                    requestDTO.getDepartmentId()
            );

            Employee employee =
                    employeeMapper.toEntity(requestDTO);

            employee.setOnboardingStatus(PENDING_VALIDATION);

            return employeeRepository.save(employee);
        }
    }

    @Scheduled(fixedDelay = 30000)
    @Transactional
    public void retryPendingEmployees() {

        List<Employee> pendingEmployees =
                employeeRepository.findByOnboardingStatus(PENDING_VALIDATION);

        for (Employee employee : pendingEmployees) {

            try {

                // 1. Validate department again
                departmentValidationService.departmentResponseDTO(
                        employee.getDepartmentId()
                );

                // 2. Department is available → activate employee
                employee.setOnboardingStatus(ACTIVE);

                Employee savedEmployee =
                        employeeRepository.save(employee);

                // 3. Continue onboarding
                onboardingCompletionService.completeOnboarding(savedEmployee);

                logger.info(
                        "Pending employee onboarding completed. employeeId={}, departmentId={}",
                        employee.getId(),
                        employee.getDepartmentId()
                );

            } catch (DepartmentNotFoundException exception) {

                // Department no longer exists → remove employee

                logger.warn(
                        "Department not found. Removing pending employee. " +
                                "employeeId={}, departmentId={}",
                        employee.getId(),
                        employee.getDepartmentId()
                );

                employeeRepository.delete(employee);

            } catch (DepartmentServiceNotAvailableException exception) {

                // Department service is still unavailable.
                // Leave employee as PENDING_VALIDATION.

                logger.warn(
                        "Department service still unavailable. " +
                                "Employee remains PENDING_VALIDATION. employeeId={}, departmentId={}",
                        employee.getId(),
                        employee.getDepartmentId()
                );
            }
        }
    }
}
