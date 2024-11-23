package emp.event_management_platform.controller;

import emp.event_management_platform.entities.AppUser;
import emp.event_management_platform.entities.Event;
import emp.event_management_platform.service.IEvent;
import emp.event_management_platform.service.IParticipant;
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
    private IParticipant participant;

    @GetMapping("/user/event/getAll")
    public String getAllEvents(Model model) {
        AppUser user = participant.getUserAuth();
        List<Event> events = iEvent.getAll();
        model.addAttribute("events", events);
        model.addAttribute("user", user);
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

    @GetMapping("/admin/event/edit")
    public String editEvent(@RequestParam(name = "id") Long id, Model model) {
        Event event = iEvent.getEventById(id);
        model.addAttribute("event", event);
        return "/events/editevent";
    }

    @PostMapping("/admin/event/update/{id}")
    public String updateEvent(@PathVariable("id") Long id,@Valid Event event, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "/events/editevent";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = event.getDate().format(formatter);
        LocalDate formattedLocalDate = LocalDate.parse(formattedDate, formatter);
        event.setDate(formattedLocalDate);
        iEvent.updateEvent(id,event);
        return "redirect:/user/event/getAll";
    }

    @GetMapping("/admin/event/delete")
    public String deleteEvent(@RequestParam(name = "id") Long id, Model model) {
        Event event = iEvent.getEventById(id);

        iEvent.deleteEvent(event);
        return "redirect:/user/event/getAll";
    }

    @GetMapping("/admin/users")
    public String getAllUsers(Model model) {
        List<AppUser> users = participant.getAllUsers();

        System.out.println(users.size());
        for (AppUser user : users) {
            System.out.println(user.getUsername());
        }
        model.addAttribute("users", users);
        return "/admin/users";
    }

    @GetMapping("/admin/participants")
    public String getAllParticipants(Model model) {
        List<AppUser> participants = participant.getAllParticipants();

        System.out.println(participants.size());

        model.addAttribute("participants", participants);
        return "/admin/participants";
    }

    @GetMapping("/admin/waiting_users")
    public String getAllWaiting_list(Model model) {
        List<AppUser> waiting_users = participant.getAllWaiting_list();

        System.out.println(waiting_users.size());

        model.addAttribute("waiting_users", waiting_users);
        return "/admin/waiting_users";
    }

}
