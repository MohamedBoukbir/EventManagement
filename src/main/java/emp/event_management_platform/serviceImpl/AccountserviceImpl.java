package emp.event_management_platform.serviceImpl;

import emp.event_management_platform.entities.AppRole;
import emp.event_management_platform.entities.AppUser;
import emp.event_management_platform.repo.AppRoleRepository;
import emp.event_management_platform.repo.AppUserRepository;
import emp.event_management_platform.service.Accountservice;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountserviceImpl implements Accountservice {

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmpassword) {
       AppUser appuser = appUserRepository.findByUsername(username);
       if(appuser !=null) throw new RuntimeException("this user already exist");
       if (!password.equals(confirmpassword)) throw new RuntimeException("Password not match");
       appuser=AppUser.builder()
               .userId(UUID.randomUUID().toString())
               .username(username)
               .password(passwordEncoder.encode(password))
               .email(email)
               .build();
        return appUserRepository.save(appuser);
    }

    @Override
    public AppRole addNewRole(String roleName) {
        AppRole appRole =appRoleRepository.findById(roleName).orElse(null);
        if(appRole!=null) throw new RuntimeException("role already exist");
        appRole= AppRole.builder()
                .role(roleName)
                .build();
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
     AppRole appRole = appRoleRepository.findById(roleName).get();
     AppUser appUser = appUserRepository.findByUsername(username);
     appUser.getRoles().add(appRole);
//     appUserRepository.save(appUser);

    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {
        AppRole appRole = appRoleRepository.findById(roleName).get();
        AppUser appUser = appUserRepository.findByUsername(username);
        appUser.getRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
