package emp.event_management_platform.repo;

import emp.event_management_platform.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
  AppUser findByUsername(String username);
  AppUser findByEmail(String email);
  @Query("SELECT u FROM AppUser u JOIN u.roles r WHERE r.role = 'USER'")
  List<AppUser> findAllByRoleUser();
}
