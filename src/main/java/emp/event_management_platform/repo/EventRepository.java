package emp.event_management_platform.repo;

import emp.event_management_platform.entities.AppUser;
import emp.event_management_platform.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT DISTINCT p FROM Event e JOIN e.participants p")
    List<AppUser> findAllParticipants();
    @Query("SELECT DISTINCT p FROM Event e JOIN e.waiting_list p")
    List<AppUser> findAllWaiting_list();
}
