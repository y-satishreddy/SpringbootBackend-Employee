package pl.piomin.services.employee.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piomin.services.employee.entity.OutboxEvent;
import pl.piomin.services.employee.repository.OutboxEventRepository;

import java.util.Optional;

@Service
public class OutboxLockService {

    private final OutboxEventRepository outboxEventRepository;

    public OutboxLockService(
            OutboxEventRepository outboxEventRepository) {
        this.outboxEventRepository = outboxEventRepository;
    }

    @Transactional
    public Optional<OutboxEvent> claimEvent(Long id) {

        Optional<OutboxEvent> optionalEvent =
                outboxEventRepository.findByIdForUpdate(id);

        if (optionalEvent.isEmpty()) {
            return Optional.empty();
        }

        OutboxEvent event = optionalEvent.get();

        if (event.isProcessing()) {
            return Optional.empty();
        }

        event.setProcessing(true);

        return Optional.of(event);
    }
    @Transactional
    public void releaseEvent(Long id) {

        outboxEventRepository.findById(id)
                .ifPresent(event -> {
                    event.setProcessing(false);
                });
    }
    @Transactional
    public void deleteEvent(Long id) {

        outboxEventRepository.deleteById(id);
    }
}
