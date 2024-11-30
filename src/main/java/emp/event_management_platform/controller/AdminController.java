package emp.event_management_platform.controller;

import emp.event_management_platform.entities.AppPayment;
import emp.event_management_platform.entities.AppUser;
import emp.event_management_platform.entities.Event;
import emp.event_management_platform.service.IEvent;
import emp.event_management_platform.service.IParticipant;
import emp.event_management_platform.service.IPaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class AdminController {
    private IEvent iEvent;
    private IParticipant participant;
    private IPaymentService paymentService;

    @GetMapping("/admin/event/getAll")
    public String getAllEvents(Model model) {
        AppUser user = participant.getUserAuth();
        List<Event> events = iEvent.getAll();
        for (Event event : events) {
            System.out.println(event.getId());
        }
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
        return "redirect:/admin/event/getAll";
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
        return "redirect:/admin/event/getAll";
    }

    @PostMapping("/admin/event/delete")
    public String deleteEvent(@RequestParam(name = "id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Event event = iEvent.getEventById(id);

        iEvent.deleteEvent(event);
        redirectAttributes.addFlashAttribute("successMessage", "Event successfully deleted!");
        return "redirect:/admin/event/getAll";
    }

    @GetMapping("/admin/users")
    public String getAllUsers(Model model) {
        List<AppUser> users = participant.getAllUsers();

        System.out.println(users.size());
        for (AppUser user : users) {
            System.out.println(user.getId());
        }
        model.addAttribute("users", users);
        return "/admin/users";
    }

    @PostMapping("/admin/user/delete")
    public String deleteUser(@RequestParam(name = "id") Long id, Model model, RedirectAttributes redirectAttributes) {
        AppUser appUser =participant.findUserById(String.valueOf(id));
        participant.deleteUser(appUser);
        redirectAttributes.addFlashAttribute("successMessage", "User successfully deleted!");
        return "redirect:/admin/users";
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

    @GetMapping("/admin/dashboard")
    public String Dashboard(Model model){
        Double amount =paymentService.getAllPayments().stream().mapToDouble(AppPayment::getAmount).sum();
        LocalDate today = LocalDate.now();
        LocalDate nextMonth = today.plusDays(30);

        List<Event> upcomingEvents = iEvent.getAll().stream()
                .filter(event -> event.getDate().isAfter(today) && event.getDate().isBefore(nextMonth))
                .collect(Collectors.toList());
        model.addAttribute("upcomingEvents",upcomingEvents);
        model.addAttribute("amount",amount);
        model.addAttribute("events", iEvent.getAll().size());
        model.addAttribute("users", participant.getAllUsers().size());
        model.addAttribute("participants", participant.getAllParticipants().size());
        return "/admin/index";
    }



    @GetMapping("/admin/profile")
    public String profile(Model model) {
        AppUser  user = participant.getUserAuth();
        model.addAttribute("user", user);
        return "/admin/profile";
    }

    @PostMapping("/admin/update/profile")
    public String updateprofile(@Valid AppUser user,
                                @RequestParam(name = "current") String current,
                                @RequestParam(name = "password_confirmation") String password_confirmation,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        // Vérification des erreurs de validation
        if (bindingResult.hasErrors()) {
            System.out.println("dakchi kayn");
            return "/admin/profile";
        }
        System.out.println(current);

        // Vérification du mot de passe actuel
//        if (!userService.isCurrentPasswordValid(user, current)) {
//            bindingResult.rejectValue("current", "error.user", "Current password is incorrect.");
//            return "/admin/profile";
//        }

        // Vérification des mots de passe
//        if (!user.getPassword().equals(password_confirmation)) {
//            bindingResult.rejectValue("password_confirmation", "error.user", "Passwords do not match.");
//            return "/admin/profile";
//        }

        // Mise à jour de l'utilisateur
//        userService.updateUser(user);

        // Message de confirmation
        redirectAttributes.addFlashAttribute("success", "Profile updated successfully.");
        return "redirect:/admin/profile";
    }

}
