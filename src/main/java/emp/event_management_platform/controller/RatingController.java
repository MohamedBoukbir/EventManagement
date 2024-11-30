package emp.event_management_platform.controller;

import emp.event_management_platform.entities.AppUser;
import emp.event_management_platform.service.IParticipant;
import emp.event_management_platform.service.IRatingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class RatingController {
    private IRatingService ratingService;
    private IParticipant participant;

    @PostMapping("/user/events/rate")
    public String rateEvent(@RequestParam(name = "id") Long id,
                            @RequestParam(name = "rating") int rating,
                            RedirectAttributes redirectAttributes) {
        System.out.println("rating" +rating +"id"+id);
        AppUser user = participant.getUserAuth();
        boolean response = ratingService.AddRating(rating ,id,user.getId());
        if (response) {

            redirectAttributes.addFlashAttribute("successMessage", "Rating added successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add rating.");
        }
        return "redirect:/user/event/getAll";
    }
}
