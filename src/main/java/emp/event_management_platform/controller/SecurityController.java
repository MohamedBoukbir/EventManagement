package emp.event_management_platform.controller;
import org.springframework.security.core.Authentication;
import emp.event_management_platform.entities.AppUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
    @GetMapping("/notAuthenticated")
    public String  notAuthenticated() {
        return "notAuthenticated";
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }
    @GetMapping("/")
    public String home(){
        // Get the authenticated user’s roles
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Check if the user has the role "ADMIN" and redirect to homeAdmin
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "homeAdmin";
        }

        // Check if the user has the role "USER" and redirect to homeUser
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            return "redirect:/user/event/getAll";
        }

        // Default redirection if no role is matched
        return "redirect:/login?error"; // or any other page
    }
}
