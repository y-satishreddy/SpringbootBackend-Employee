package pl.piomin.services.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piomin.services.employee.entity.OutboxEvent;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {
}
