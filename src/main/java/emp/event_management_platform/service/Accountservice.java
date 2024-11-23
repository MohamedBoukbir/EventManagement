package emp.event_management_platform.service;


import emp.event_management_platform.entities.AppRole;
import emp.event_management_platform.entities.AppUser;

public interface Accountservice {
    AppUser addNewUser(String username, String password , String email , String confirmpassword);
    AppRole addNewRole(String roleName);
    void addRoleToUser(String username, String roleName);
    void removeRoleFromUser(String username, String roleName);
    AppUser loadUserByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
