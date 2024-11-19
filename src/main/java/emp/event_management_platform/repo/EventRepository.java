package emp.event_management_platform.repo;

import emp.event_management_platform.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
