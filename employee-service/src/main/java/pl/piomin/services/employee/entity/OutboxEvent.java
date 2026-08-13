package pl.piomin.services.employee.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_events")
public class OutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventType;

    private Long aggregateId;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String payload;

    private LocalDateTime createdAt;

    protected OutboxEvent() {
    }

    public OutboxEvent(
            String eventType,
            Long aggregateId,
            String payload,
            LocalDateTime createdAt) {

        this.eventType = eventType;
        this.aggregateId = aggregateId;
        this.payload = payload;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getEventType() {
        return eventType;
    }

    public Long getAggregateId() {
        return aggregateId;
    }

    public String getPayload() {
        return payload;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}