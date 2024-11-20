package emp.event_management_platform.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(unique = true)
    @NotEmpty
    private  String username;
    @NotEmpty
    private  String password;
    @NotEmpty
    @Column(unique = true)
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppRole> roles;
    @ManyToMany(mappedBy = "participants", fetch = FetchType.EAGER)
    private List<Event> events;
    @ManyToMany(mappedBy = "waiting_list", fetch = FetchType.EAGER)
    private List<Event> waiting_events;
}
