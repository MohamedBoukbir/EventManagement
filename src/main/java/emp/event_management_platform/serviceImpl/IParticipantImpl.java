package emp.event_management_platform.serviceImpl;

import emp.event_management_platform.entities.AppUser;
import emp.event_management_platform.entities.Event;
import emp.event_management_platform.service.IParticipant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IParticipantImpl implements IParticipant {
    @Override
    public String registerForEvent(AppUser user, Event event) {
        if (event.getParticipants().size() >= event.getCapacity()) {
            return "The event has exceeded the capacity: false";
        }
        event.getParticipants().add(user); // Ajout de l'utilisateur à la liste de participants de l'événement
        user.getEvents().add(event); // Ajout de l'événement à la liste d'événements de l'utilisateur
        return "Your event has been registered: true";
    }

    @Override
    public String cancelRegistration(AppUser user, Event event) {
        event.getParticipants().remove(user); // Retire l'utilisateur de la liste des participants de l'événement
        user.getEvents().remove(event); // Retire l'événement de la liste d'événements de l'utilisateur
        return "Your event has been cancelled: true";

    }
}
