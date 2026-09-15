package pl.piomin.services.department.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.piomin.services.department.event.EmployeeOnboardEvent;
import pl.piomin.services.department.service.DepartmentOnboardingService;

@Component
public class EmployeeOnboardListener {

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeOnboardListener.class);

    private final DepartmentOnboardingService onboardingService;

    public EmployeeOnboardListener(
            DepartmentOnboardingService onboardingService) {

        this.onboardingService = onboardingService;
    }

    @KafkaListener(topics = "employee-onboarded")
    public void handleOnboardEmployee(EmployeeOnboardEvent event) {

        logger.info(
                "Received employee onboarding event: {}",
                event.getName()
        );

        onboardingService.handleEmployeeOnboard(event);
    }
}