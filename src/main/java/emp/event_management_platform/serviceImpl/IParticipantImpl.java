package emp.event_management_platform.serviceImpl;

import emp.event_management_platform.entities.AppUser;
import emp.event_management_platform.entities.Event;
import emp.event_management_platform.repo.AppUserRepository;
import emp.event_management_platform.repo.EventRepository;
import emp.event_management_platform.service.IParticipant;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class IParticipantImpl implements IParticipant {
    private AppUserRepository appUserRepository;
    private EventRepository eventRepository;


    @Override
    public AppUser getUserAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = appUserRepository.findByUsername(authentication.getName());
        return user;
    }

    @Override
    public String registerWaitingForEvent(AppUser appUser, Event event) {
        event.getWaiting_list().add(appUser);
        appUser.getWaiting_events().add(event);
        return "Your are in the waiting list: true";
    }

    @Override
    public List<AppUser> getAllUsers() {
        return  appUserRepository.findAllByRoleUser();
    }

    @Override
    public List<AppUser> getAllParticipants() {
        return eventRepository.findAllParticipants() ;
    }
    @Override
    public List<AppUser> getAllWaiting_list() {
        return eventRepository.findAllWaiting_list() ;
    }

    @Override
    public String registerForEvent(AppUser user, Event event) {
        if (event.getParticipants().size() >= event.getCapacity()) {
            return "The event has exceeded the capacity: false";
        }
        event.getParticipants().add(user);
        user.getEvents().add(event);
        return "Your event has been registered: true";
    }

    @Override
    public String cancelRegistration(AppUser user, Event event) {
        event.getParticipants().remove(user);
        user.getEvents().remove(event);
        return "Your event has been cancelled: true";

    }
}
