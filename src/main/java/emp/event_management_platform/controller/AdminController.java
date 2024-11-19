package emp.event_management_platform.controller;

import emp.event_management_platform.entities.Event;
import emp.event_management_platform.service.IEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@AllArgsConstructor
public class AdminController {
    private IEvent iEvent;

    @GetMapping("/user/event/getAll")
    public String getAllEvents(Model model) {
        List<Event> events = iEvent.getAll();
        model.addAttribute("events", events);
        return "/events/listEvents";
    }

    @GetMapping("/admin/event/add")
    public String addEvent(Model model) {
        model.addAttribute("event", new Event());
        return "/events/addevent";
    }

    @PostMapping("/admin/event/create")
    public String createEvent(@Valid Event event, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "/events/addevent";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = event.getDate().format(formatter);
        LocalDate formattedLocalDate = LocalDate.parse(formattedDate, formatter);
        event.setDate(formattedLocalDate);
        iEvent.createEvent(event);
        return "redirect:/user/event/getAll";
    }

    @GetMapping("/Admin/event/edit")
    public String editEvent(@RequestParam(name = "id") Long id, Model model) {
        Event event = iEvent.getEventById(id);
        model.addAttribute("event", event);
        return "/events/editevent";
    }

    @PostMapping("/Admin/event/update/{id}")
    public String updateEvent(@PathVariable("id") Long id,@Valid Event event, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "/events/editevent";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = event.getDate().format(formatter);
        LocalDate formattedLocalDate = LocalDate.parse(formattedDate, formatter);
        event.setDate(formattedLocalDate);
        iEvent.updateEvent(id,event);
        return "redirect:/user/event/getAll";
    }

    @PostMapping("/Admin/event/delete/{id}")
    public String deleteEvent(@PathVariable Long eventId) {
        Event event = iEvent.getEventById(eventId);
        iEvent.deleteEvent(event);
        return "redirect:/user/event/getAll";
    }
}
