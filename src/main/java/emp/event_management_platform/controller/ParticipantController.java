package emp.event_management_platform.controller;

import emp.event_management_platform.entities.AppUser;
import emp.event_management_platform.entities.Event;
import emp.event_management_platform.repo.AppUserRepository;
import emp.event_management_platform.service.IEmailSend;
import emp.event_management_platform.service.IEvent;
import emp.event_management_platform.service.IParticipant;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class ParticipantController {
    private IParticipant participant;
    private IEvent iEvent;
    private AppUserRepository appUserRepository;
    private IEmailSend iEmailSend;

@GetMapping("user/register/event")
    public String  registerForEvent(@RequestParam Long id, RedirectAttributes redirectAttributes){
    String response;
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    AppUser user = appUserRepository.findByUsername(authentication.getName());
    Event event = iEvent.getEventById(id);

    if (event.getParticipants().contains(user)) {
         response = participant.cancelRegistration(user, event);
        sendNotification("Cancellation from Event", response.split(":")[0]);
    }else{
        response = participant.registerForEvent(user, event);
        sendNotification("Participation in Event", response.split(":")[0]);
    }
    redirectAttributes.addFlashAttribute("message", response);
    return "redirect:/user/event/getAll" ;
    }

    public  String sendNotification(String subject, String body ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = appUserRepository.findByUsername(authentication.getName());
        iEmailSend.sendEmail(user.getEmail(), subject,body);
        return "redirect:/user/event/getAll" ;
    }

}
