package pl.piomin.services.employee.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piomin.services.employee.entity.Employee;
import pl.piomin.services.employee.entity.OutboxEvent;
import pl.piomin.services.employee.event.EmployeeOnboardEvent;
import pl.piomin.services.employee.event.OutboxCreatedEvent;
import pl.piomin.services.employee.mapper.EmployeeMapper;

@Service
public class EmployeeOnboardingCompletionService {

    private final EmployeeMapper employeeMapper;
    private final OutboxService outboxService;
    private final ApplicationEventPublisher publisher;

    public EmployeeOnboardingCompletionService(
            EmployeeMapper employeeMapper,
            OutboxService outboxService,
            ApplicationEventPublisher publisher) {

        this.employeeMapper = employeeMapper;
        this.outboxService = outboxService;
        this.publisher = publisher;
    }

    @Transactional
    public Employee completeOnboarding(Employee employee) {

        EmployeeOnboardEvent employeeOnboardEvent =
                employeeMapper.toActiveEmployee(employee);

        OutboxEvent outboxEvent =
                outboxService.saveOutboxEvent(employeeOnboardEvent);

        publisher.publishEvent(
                new OutboxCreatedEvent(outboxEvent)
        );

        return employee;
    }
}