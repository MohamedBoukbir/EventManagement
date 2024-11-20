package emp.event_management_platform.serviceImpl;

import emp.event_management_platform.entities.Event;
import emp.event_management_platform.repo.EventRepository;
import emp.event_management_platform.service.IEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@AllArgsConstructor
public class IEventImpl implements IEvent {
    private  EventRepository eventRepository;
    @Override
    public boolean isFull(Event event) {
        return event.getParticipants() != null && event.getParticipants().size() >= event.getCapacity();
    }

    @Override
    public void createEvent(Event event) {
        eventRepository.save(event);
    }
    @Override
    public void updateEvent(Long id ,Event event) {
        Event updateEvent = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
         System.out.println("id is  "+updateEvent.getId());
        updateEvent.setTitle(event.getTitle());
        updateEvent.setDescription(event.getDescription());
        updateEvent.setLocation(event.getLocation());
        updateEvent.setDate(event.getDate());
        updateEvent.setCapacity(event.getCapacity());
        updateEvent.setType(event.getType());
        updateEvent.setPrice(event.getPrice());
//        updateEvent.setParticipants(event.getParticipants());
        eventRepository.save(updateEvent);
    }
    @Override
    public void deleteEvent(Event event) {
        Event deleteEvent = eventRepository.findById(event.getId())
                .orElseThrow(() -> new RuntimeException("Event not found"));
        eventRepository.delete(deleteEvent);

    }

    @Override
    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }
}
