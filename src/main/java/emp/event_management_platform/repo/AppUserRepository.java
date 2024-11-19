package emp.event_management_platform.repo;

import emp.event_management_platform.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
  AppUser findByUsername(String username);
}
