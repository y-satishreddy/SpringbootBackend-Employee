package pl.piomin.services.employee.event;

import pl.piomin.services.employee.entity.OutboxEvent;

public class OutboxCreatedEvent {
    private final OutboxEvent outboxEvent;

    public OutboxCreatedEvent(OutboxEvent outboxEvent) {
        this.outboxEvent = outboxEvent;
    }

    public OutboxEvent getOutboxEvent() {
        return outboxEvent;
    }
}
