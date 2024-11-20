package emp.event_management_platform.service;

import emp.event_management_platform.entities.Event;

import java.util.List;

public interface IEvent {
    boolean isFull(Event event);
    void createEvent(Event event);
    void updateEvent(Long id ,Event event);
    void deleteEvent(Event event);
     Event getEventById(Long id);
    List<Event> getAll();

}
