package emp.event_management_platform.controller;
import emp.event_management_platform.service.Accountservice;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import emp.event_management_platform.entities.AppUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class SecurityController  {
    private Accountservice accountservice;
    @GetMapping("/403")
    public String  err403() {
        return "error-403";
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }
    @GetMapping("register")
    public String register(Model model){
        model.addAttribute("user", new AppUser());
   return "register";
    }

    @PostMapping("/signup")
    public String signUp(
            @Valid @ModelAttribute("user") AppUser user,
            @RequestParam("confirmPassword") String confirmPassword,
            BindingResult bindingResult,
            Model model) {

        // Vérification des erreurs de validation
        if (bindingResult.hasErrors()) {
            return "register"; // Retourne au formulaire avec les erreurs
        }

        // Vérifier si le nom d'utilisateur existe déjà
        if (accountservice.existsByUsername(user.getUsername())) {
            model.addAttribute("usernameError", "Username already taken");
            return "register";
        }

        // Vérifier si l'email existe déjà
        if (accountservice.existsByEmail(user.getEmail())) {
            model.addAttribute("emailError", "Email already registered");
            return "register";
        }

        // Vérifier si les mots de passe correspondent
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("confirmPasswordError", "Passwords do not match");
            return "register";
        }

        // Ajouter le nouvel utilisateur
        accountservice.addNewUser(user.getUsername(), user.getPassword(),
                user.getFirstname(), user.getLastname(), user.getAddress(), user.getCountry(),
                user.getGender(), user.getEmail(), confirmPassword);

        // Ajouter le rôle USER à l'utilisateur
        accountservice.addRoleToUser(user.getUsername(), "USER");

        return "login"; // Redirige vers la page de connexion après succès
    }



    @GetMapping("/")
    public String home(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "redirect:/admin/dashboard";
        }

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            return "redirect:/user/dashboard";
        }
        return "redirect:/login?error";
    }
}
