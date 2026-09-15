package pl.piomin.services.employee.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pl.piomin.services.employee.entity.OutboxEvent;
import pl.piomin.services.employee.event.EmployeeOnboardEvent;
import pl.piomin.services.employee.repository.OutboxEventRepository;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

@Service
public class OutboxPublisherServiceImpl implements OutboxPublisherService {

    private static final Logger logger =
            LoggerFactory.getLogger(OutboxPublisherServiceImpl.class);

    private final KafkaTemplate<String, EmployeeOnboardEvent> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final OutboxLockService outboxLockService;
    private final OutboxEventRepository outboxEventRepository;

    public OutboxPublisherServiceImpl(
            KafkaTemplate<String, EmployeeOnboardEvent> kafkaTemplate,
            ObjectMapper objectMapper,
            OutboxLockService outboxLockService,
            OutboxEventRepository outboxEventRepository) {

        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.outboxLockService = outboxLockService;
        this.outboxEventRepository = outboxEventRepository;
    }


    public void publish(Long outboxEventId) {

        Optional<OutboxEvent> optionalEvent =
                outboxLockService.claimEvent(outboxEventId);

        if (optionalEvent.isEmpty()) {

            logger.info(
                    "OutboxEvent {} is already being processed or no longer exists",
                    outboxEventId
            );

            return;
        }

        OutboxEvent outboxEvent = optionalEvent.get();

        try {

            EmployeeOnboardEvent employeeOnboardEvent =
                    objectMapper.readValue(
                            outboxEvent.getPayload(),
                            EmployeeOnboardEvent.class
                    );

            logger.info(
                    "Publishing OutboxEvent {} to Kafka",
                    outboxEvent.getId()
            );

            kafkaTemplate.send(
                    "employee-onboarded",
                    String.valueOf(outboxEvent.getAggregateId()),
                    employeeOnboardEvent
            ).whenComplete((result, exception) -> {

                if (exception == null) {

                    logger.info(
                            "OutboxEvent {} successfully published to Kafka",
                            outboxEvent.getId()
                    );

                    outboxEventRepository.deleteById(
                            outboxEvent.getId()
                    );

                } else {

                    logger.error(
                            "Failed to publish OutboxEvent {}. " +
                                    "It will remain in the database for retry.",
                            outboxEvent.getId(),
                            exception
                    );

                    outboxLockService.releaseEvent(
                            outboxEvent.getId()
                    );
                }
            });

        } catch (JacksonException e) {

            logger.error(
                    "Failed to deserialize OutboxEvent {}",
                    outboxEvent.getId(),
                    e
            );

            outboxLockService.releaseEvent(
                    outboxEvent.getId()
            );
        }
    }
}