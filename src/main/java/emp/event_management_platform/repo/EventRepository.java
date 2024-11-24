package emp.event_management_platform.repo;

import emp.event_management_platform.entities.AppUser;
import emp.event_management_platform.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT DISTINCT p FROM Event e JOIN e.participants p")
    List<AppUser> findAllParticipants();
    @Query("SELECT DISTINCT w FROM Event e JOIN e.waitinglist w")
    List<AppUser> findAllWaitinglist();

    // Méthode pour récupérer les événements d'un utilisateur donné
    List<Event> findByParticipantsContains(AppUser user);
    List<Event> findByWaitinglistContains(AppUser user);
}
