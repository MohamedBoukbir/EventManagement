package emp.event_management_platform.security;

import emp.event_management_platform.serviceImpl.UserDetailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private PasswordEncoder passwordEncoder;
    private UserDetailServiceImpl userDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {  // la creation d'un filtre pour l'autentification
        httpSecurity.formLogin().loginPage("/login").defaultSuccessUrl("/",true).permitAll(); // pour affichier une page login dans le cas quant je en veux acceder a notre appilcation
        httpSecurity
                .authorizeHttpRequests()
                .requestMatchers("/register","/webjars/**", "/static/**", "/css/**", "/js/**", "/images/**")
                .permitAll(); // Autorise les ressources statiques sans authentification
        httpSecurity.rememberMe();
        httpSecurity.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER"); // autorizer les user qunat un role USER
        httpSecurity.authorizeHttpRequests().requestMatchers("/payment/**").hasRole("USER");
        httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN"); // autorizer les user qunat un role ADMIN
        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();  // tous les requetes nisisite une autentification
        httpSecurity.exceptionHandling().accessDeniedPage("/403");
        httpSecurity.userDetailsService(userDetailService);
        return httpSecurity.build();
    }
}
