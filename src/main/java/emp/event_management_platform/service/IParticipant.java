package emp.event_management_platform.service;

import emp.event_management_platform.entities.AppUser;
import emp.event_management_platform.entities.Event;

public interface IParticipant {
   String registerForEvent(AppUser appUser, Event event);
   String cancelRegistration(AppUser appuser,Event event);
   AppUser getUserAuth();
   String  registerWaitingForEvent(AppUser appUser, Event event);
}
