package emp.event_management_platform;

import emp.event_management_platform.service.Accountservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EventManagementPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventManagementPlatformApplication.class, args);
    }

   // @Bean
    CommandLineRunner commandLineRunnerUserDetails(Accountservice accountservice){
        return args -> {
            accountservice.addNewRole("USER");
            accountservice.addNewRole("ADMIN");
            accountservice.addNewUser("user1","1234","mohamed","razi","kenitra","Morocco","Male","user1@gmail.com","1234");
            accountservice.addNewUser("user2","1234","mohamed","razi","kenitra","Morocco","Male","user2@gmail.com","1234");
            accountservice.addNewUser("admin","1234","mohamed","razi","kenitra","Morocco","Male","admin@gmail.com","1234");
            accountservice.addRoleToUser("admin","ADMIN");
//            accountservice.addRoleToUser("admin","USER");
            accountservice.addRoleToUser("user1","USER");
            accountservice.addRoleToUser("user2","USER");


        };
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
